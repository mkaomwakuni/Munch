package com.est.munchy.viewModels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.datastore.dataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.est.munchy.data.DataStoreRepository
import com.est.munchy.data.Repository
import com.est.munchy.data.database.local.entities.BookedRecipeEntity
import com.est.munchy.data.database.local.entities.FoodJokesEntity
import com.est.munchy.data.database.local.entities.RecipeEntity
import com.est.munchy.domain.model.FoodJokes
import com.est.munchy.domain.model.MunchRecipe
import com.est.munchy.utils.AppConstants.Companion.API_KEY
import com.est.munchy.utils.AppConstants.Companion.DEFAULT_DIET_TYPE
import com.est.munchy.utils.AppConstants.Companion.DEFAULT_MEAL_TYPE
import com.est.munchy.utils.AppConstants.Companion.DEFAULT_RECIPES_NUMBER
import com.est.munchy.utils.AppConstants.Companion.QUERY_ADD_RECIPE_INFORMATION
import com.est.munchy.utils.AppConstants.Companion.QUERY_API_KEY
import com.est.munchy.utils.AppConstants.Companion.QUERY_DIET
import com.est.munchy.utils.AppConstants.Companion.QUERY_FILL_INGREDIENTS
import com.est.munchy.utils.AppConstants.Companion.QUERY_NUMBER
import com.est.munchy.utils.AppConstants.Companion.QUERY_SEARCH
import com.est.munchy.utils.AppConstants.Companion.QUERY_TYPE
import com.est.munchy.utils.NetworkResponse
import com.est.munchy.viewModels.events.MainEvent
import com.est.munchy.viewModels.states.MainUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    private val dataStoreRepository: DataStoreRepository,
    application: Application
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.local.readRecipes().collect { recipes ->
                _uiState.update { it.copy(recipes = recipes.flatMap {it.recipe.result ?: emptyList()}) }
            }
            repository.local.readBooked().collect { favorites ->
                _uiState.update { it.copy(favoriteRecipes = favorites) }
            }
            repository.local.readJokes().collect { jokes ->
                jokes.firstOrNull()?.let { joke ->
                    _uiState.update { it.copy(foodJoke = joke.foodJoke) }
                }
            }
            dataStoreRepository.lastUpdateTime.collect { lastUpdateTime ->
                checkForRecipeUpdates(lastUpdateTime)
            }
        }
    }

    private suspend fun checkForRecipeUpdates(lastUpdate: Long) {
        val currentTime = System.currentTimeMillis()
        val updateInterval = 24 * 60 * 60 * 1000 // 24 hours in milliseconds

        // Check if we need to update (more than 24 hours since last update)
        if (currentTime - lastUpdate > updateInterval) {
            if (hasInternetConnection()) {
                try {
                    // Fetch new recipes from API
                    val response = repository.remote.getRecipes(applyQueries())
                    when (val result = handleFoodRecipesResponse(response)) {
                        is NetworkResponse.SuccessResponse -> {
                            // Update local database with new recipes
                            result.data?.let { recipe ->
                                offlineCacheRecipes(recipe)
                                // Save new update time
                                dataStoreRepository.saveLastUpdateTime(currentTime)
                            }
                        }

                        is NetworkResponse.ErrorResponse -> {
                            _uiState.update { it.copy(error = result.message) }
                        }

                        is NetworkResponse.Loading -> {
                            _uiState.update { it.copy(isLoading = true) }
                        }
                    }
                } catch (e: Exception) {
                    _uiState.update { it.copy(error = "Error updating recipes: ${e.message}") }
                }
            }
        }
        repository.local.readRecipes().collect { recipes ->
            _uiState.update { it.copy(
                recipes = recipes.flatMap {it.recipe.result ?: emptyList()})
            }
        }
    }

    fun forceUpdateRefresh() {
        viewModelScope.launch {
            checkForRecipeUpdates(0L)
        }
    }

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.SearchRecipes -> {
                searchRecipes(event.query)
            }
            is MainEvent.RefreshRecipes -> {
                getRecipes(applyQueries())
            }
            is MainEvent.AddToFavorites -> {
                viewModelScope.launch {
                    try {
                        val bookedRecipe = BookedRecipeEntity(result = event.recipe)
                        repository.local.insertBooked(bookedRecipe)
                        Timber.tag("MainViewModel")
                            .d("Recipe saved to favorites: ${event.recipe.title}")
                    } catch (e: Exception) {
                        Timber.tag("MainViewModel").e("Error saving recipe: ${e.message}")
                        _uiState.update { it.copy(error = "Failed to save recipe") }
                    }
                    insertFavoriteRecipe(BookedRecipeEntity(result = event.recipe))
                }
            }
            is MainEvent.RemoveFromFavorites -> {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        repository.local.deleteBookedRecipe(event.recipe)
                        Timber.tag("MainViewModel").d("Recipe removed from favorites: ${event.recipe.result.title}")
                    } catch (e: Exception) {
                        Log.e("MainViewModel", "Error removing recipe: ${e.message}")
                        _uiState.update { it.copy(error = "Failed to remove recipe") }
                    }
                }
            }
            is MainEvent.GetFoodJoke -> {
                getFoodJoke(API_KEY)
            }
            MainEvent.ClearError -> {
                _uiState.update { it.copy(error = null) }
            }
        }
    }

    private fun insertRecipes(recipesEntity: RecipeEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertRecipes(recipesEntity)
        }

    fun insertFavoriteRecipe(bookedRecipeEntity: BookedRecipeEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertBooked(bookedRecipeEntity)
        }

    private fun insertFoodJoke(foodJokeEntity: FoodJokesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertJokes(foodJokeEntity)
        }

    fun deleteFavoriteRecipe(bookedRecipeEntity: BookedRecipeEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteBookedRecipe(bookedRecipeEntity)
        }

    fun deleteAllFavoriteRecipes() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.local.deleteAllBooked()
                Timber.tag("MainViewModel").d("All favorite recipes deleted")
            } catch (e: Exception) {
                Timber.tag("MainViewModel").e("Error deleting all recipes: ${e.message}")
                _uiState.update { it.copy(error = "Failed to delete all recipes") }
            }
        }
    }

    private fun getRecipes(queries: Map<String, String>) = viewModelScope.launch {
        getRecipesSafeCall(queries)
    }

    private fun searchRecipes(query: String) = viewModelScope.launch {
        searchRecipesSafeCall(applySearchQuery(query))
    }

    private fun getFoodJoke(apiKey: String) = viewModelScope.launch {
        getFoodJokeSafeCall(apiKey)
    }

    private suspend fun getRecipesSafeCall(queries: Map<String, String>) {
        _uiState.update { it.copy(isLoading = true) }
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getRecipes(queries)
                val result = handleFoodRecipesResponse(response)
                // Add logging
                Log.d("API_RESPONSE", "Response code: ${response.code()}")
                Log.d("API_RESPONSE", "Response body: ${response.body()}")
                Log.d("API_RESPONSE", "Response error: ${response.errorBody()?.string()}")
                when (result) {
                    is NetworkResponse.SuccessResponse -> {
                        _uiState.update { it.copy(
                            recipes = result.data?.result ?: emptyList(),
                            isLoading = false,
                            error = null

                        ) }
                        // Cache the response
                        offlineCacheRecipes(result.data!!)
                    }
                    is NetworkResponse.ErrorResponse -> {
                        _uiState.update { it.copy(
                            isLoading = false,
                            error = result.message
                        ) }
                    }
                    is NetworkResponse.Loading -> {
                        // This state is handled at the beginning of the function
                    }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(
                    isLoading = false,
                    error = "Recipes not found: ${e.message}"
                ) }
            }
        } else {
            _uiState.update { it.copy(
                isLoading = false,
                error = "No Internet Connection."
            ) }
        }
    }

    private suspend fun searchRecipesSafeCall(searchQuery: Map<String, String>) {
        _uiState.update { it.copy(isLoading = true) }
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.searchRecipes(searchQuery)
                _uiState.update { it.copy(
                    recipes = handleFoodRecipesResponse(response).data?.result ?: emptyList(),
                    isLoading = false
                ) }
            } catch (e: Exception) {
                _uiState.update { it.copy(
                    isLoading = false,
                    error = "Recipes not found."
                ) }
            }
        } else {
            _uiState.update { it.copy(
                isLoading = false,
                error = "No Internet Connection."
            ) }
        }
    }

    private suspend fun getFoodJokeSafeCall(apiKey: String) {
        _uiState.update { it.copy(isLoading = true) }
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getFoodJoke(apiKey)
                val foodJoke = handleFoodJokeResponse(response).data
                _uiState.update { it.copy(
                    foodJoke = foodJoke,
                    isLoading = false
                ) }

                foodJoke?.let { joke ->
                    offlineCacheFoodJoke(joke)
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(
                    isLoading = false,
                    error = "Food joke not found."
                ) }
            }
        } else {
            _uiState.update { it.copy(
                isLoading = false,
                error = "No Internet Connection."
            ) }
        }
    }

    private fun offlineCacheRecipes(munchRecipe: MunchRecipe) {
        val recipesEntity = RecipeEntity(munchRecipe)
        insertRecipes(recipesEntity)
    }

    private fun offlineCacheFoodJoke(foodJoke: FoodJokes) {
        val foodJokeEntity = FoodJokesEntity(foodJoke)
        insertFoodJoke(foodJokeEntity)
    }

    private fun handleFoodRecipesResponse(response: Response<MunchRecipe>): NetworkResponse<MunchRecipe> {
        return when {
            response.message().toString().contains("timeout") -> {
                NetworkResponse.ErrorResponse("Timeout")
            }
            response.code() == 402 -> {
                NetworkResponse.ErrorResponse("API Key Limited.")
            }
            !response.isSuccessful -> {
                NetworkResponse.ErrorResponse(response.message())
            }
            response.body() == null -> {
                NetworkResponse.ErrorResponse("Empty response from server")
            }
            response.body()?.result == null -> {
                NetworkResponse.ErrorResponse("No results found")
            }
            response.body()?.result?.isEmpty() == true -> {
                NetworkResponse.ErrorResponse("Recipes not found.")
            }
            else -> {
                NetworkResponse.SuccessResponse(response.body()!!)
            }
        }
    }


    private fun handleFoodJokeResponse(response: Response<FoodJokes>): NetworkResponse<FoodJokes> {
        return when {
            response.message().toString().contains("timeout") -> {
                NetworkResponse.ErrorResponse("Timeout")
            }
            response.code() == 402 -> {
                NetworkResponse.ErrorResponse("API Key Limited.")
            }
            response.isSuccessful -> {
                NetworkResponse.SuccessResponse(response.body()!!)
            }
            else -> {
                NetworkResponse.ErrorResponse(response.message())
            }
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    private fun applyQueries(): Map<String, String> {
        return mapOf(
            QUERY_NUMBER to DEFAULT_RECIPES_NUMBER,
            QUERY_API_KEY to API_KEY,
            QUERY_TYPE to DEFAULT_MEAL_TYPE,
            QUERY_DIET to DEFAULT_DIET_TYPE,
            QUERY_ADD_RECIPE_INFORMATION to "true",
            QUERY_FILL_INGREDIENTS to "true"
        )
    }

    private fun applySearchQuery(searchQuery: String): Map<String, String> {
        return mapOf(
            QUERY_SEARCH to searchQuery,
            QUERY_NUMBER to DEFAULT_RECIPES_NUMBER,
            QUERY_API_KEY to API_KEY,
            QUERY_ADD_RECIPE_INFORMATION to "true",
            QUERY_FILL_INGREDIENTS to "true"
        )
    }
}