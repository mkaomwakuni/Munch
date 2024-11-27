package com.est.munchy.viewModels

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
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.est.munchy.data.Repository
import com.est.munchy.data.database.local.entities.BookedRecipeEntity
import com.est.munchy.data.database.local.entities.FoodJokeEntity
import com.est.munchy.data.database.local.entities.RecipeEntity
import com.est.munchy.domain.model.FoodJokes
import com.est.munchy.domain.model.MunchRecipe
import com.est.munchy.utils.AppConstants.Companion.API_KEY
import com.est.munchy.utils.NetworkResponse
import com.est.munchy.viewModels.events.MainEvent
import com.est.munchy.viewModels.states.MainUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.local.readRecipes().collect { recipes ->
                _uiState.update { it.copy(recipes = recipes.flatMap {it.recipe.result}) }
            }
            repository.local.readBooked().collect { favorites ->
                _uiState.update { it.copy(favoriteRecipes = favorites) }
            }
            repository.local.readJokes().collect { jokes ->
                jokes.firstOrNull()?.let { joke ->
                    _uiState.update { it.copy(foodJoke = joke.foodJoke) }
                }
            }
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
                insertFavoriteRecipe(BookedRecipeEntity(result = event.recipe))
            }
            is MainEvent.RemoveFromFavorites -> {
                deleteFavoriteRecipe(event.recipe)
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

    private fun insertFavoriteRecipe(bookedRecipeEntity: BookedRecipeEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertBooked(bookedRecipeEntity)
        }

    private fun insertFoodJoke(foodJokeEntity: FoodJokeEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertJokes(foodJokeEntity)
        }

    private fun deleteFavoriteRecipe(bookedRecipeEntity: BookedRecipeEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteBookedRecipe(bookedRecipeEntity)
        }

    fun deleteAllFavoriteRecipes() =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteAllBooked()
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
        val foodJokeEntity = FoodJokeEntity(foodJoke)
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
            response.body()?.result?.isEmpty() == true -> {
                NetworkResponse.ErrorResponse("Recipes not found.")
            }
            response.isSuccessful -> {
                NetworkResponse.SuccessResponse(response.body()!!)
            }
            else -> {
                NetworkResponse.ErrorResponse(response.message())
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