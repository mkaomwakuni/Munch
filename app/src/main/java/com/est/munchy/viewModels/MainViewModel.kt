package com.est.munchy.viewModels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.est.munchy.data.DataStoreRepository
import com.est.munchy.data.Repository
import com.est.munchy.data.database.local.entities.BookedRecipeEntity
import com.est.munchy.data.database.local.entities.FoodJokesEntity
import com.est.munchy.data.database.local.entities.RecipeEntity
import com.est.munchy.domain.model.FoodJokes
import com.est.munchy.domain.model.ModelResult
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
        Timber.tag("MainViewModel").d("ViewModel initialized")
        observeLastUpdateTime()
    }

    private fun observeDatabase() {
        viewModelScope.launch {
            repository.local.readRecipes().collect { recipes ->
                Timber.d("New recipes from DB: ${recipes.size}")
                _uiState.update {
                    it.copy(recipes = recipes.flatMap { it.recipe.result ?: emptyList() })
                }
            }
        }

        viewModelScope.launch {
            repository.local.readBooked().collect { favorites ->
                Timber.d("New favorites from DB: ${favorites.size}")
                _uiState.update { it.copy(favoriteRecipes = favorites) }
            }
        }

        viewModelScope.launch {
            repository.local.readJokes().collect { jokes ->
                Timber.d("New jokes from DB: ${jokes.size}")
                _uiState.update {
                    it.copy(foodJoke = jokes.firstOrNull()?.foodJoke)
                }
            }
        }
    }

    private fun observeLastUpdateTime() {
        viewModelScope.launch {
            dataStoreRepository.lastUpdateTime.collect { lastUpdateTime ->
                Timber.d("Last update check: $lastUpdateTime")
                if (lastUpdateTime == 0L) { // First launch
                    observeDatabase()
                } else {
                    checkForRecipeUpdates(lastUpdateTime)
                }
            }
        }
    }

    suspend fun checkForRecipeUpdates(lastUpdate: Long) {
        val currentTime = System.currentTimeMillis()
        val updateInterval = 24 * 60 * 60 * 1000

        if (currentTime - lastUpdate > updateInterval && hasInternetConnection()) {
            try {
                Timber.d("Starting recipe refresh...")
                _uiState.update { it.copy(isLoading = true) }

                val response = repository.remote.getRecipes(applyQueries())
                handleRecipesResponse(response).let { result ->
                    if (result is NetworkResponse.SuccessResponse) {
                        result.data?.let { cacheRecipes(it) }
                        dataStoreRepository.saveLastUpdateTime(currentTime)
                    }
                }
            } catch (e: Exception) {
                Timber.e("Update failed: ${e.message}")
                _uiState.update { it.copy(error = "Update failed: ${e.message}") }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.SearchRecipes -> handleSearch(event.query)
            is MainEvent.RefreshRecipes -> forceRefresh()
            is MainEvent.AddToFavorites -> addFavorite(event.recipe)
            is MainEvent.RemoveFromFavorites -> removeFavorite(event.recipe)
            is MainEvent.GetFoodJoke -> getFoodJoke()
            MainEvent.ClearError -> clearError()
        }
    }

    private fun handleSearch(query: String) = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }
        try {
            val response = repository.remote.searchRecipes(applySearchQuery(query))
            handleRecipesResponse(response).let { result ->
                if (result is NetworkResponse.SuccessResponse) {
                    result.data?.let { cacheRecipes(it) }
                }
            }
        } catch (e: Exception) {
            Timber.e("Search failed: ${e.message}")
            _uiState.update { it.copy(error = "Search failed: ${e.message}") }
        } finally {
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    private fun forceRefresh() = viewModelScope.launch {
        Timber.d("Force refreshing recipes")
        try {
            checkForRecipeUpdates(0L)
        } catch (e: Exception) {
            Timber.e("Force refresh failed: ${e.message}")
            _uiState.update { it.copy(error = "Refresh failed") }
        }
    }

    private fun addFavorite(recipe: ModelResult) = viewModelScope.launch {
        try {
            repository.local.insertBooked(BookedRecipeEntity(result = recipe))
//            Timber.d("Added favorite: ${recipe.title}")
        } catch (e: Exception) {
            Timber.e("Save failed: ${e.message}")
            _uiState.update { it.copy(error = "Save failed") }
        }
    }

    private fun removeFavorite(recipe: BookedRecipeEntity) = viewModelScope.launch {
        try {
            repository.local.deleteBookedRecipe(recipe)
            Timber.d("Removed favorite: ${recipe.result.title}")
        } catch (e: Exception) {
            Timber.e("Remove failed: ${e.message}")
            _uiState.update { it.copy(error = "Remove failed") }
        }
    }

    private fun getFoodJoke() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }
        try {
            val response = repository.remote.getFoodJoke(API_KEY)
            when (val result = handleJokeResponse(response)) {
                is NetworkResponse.SuccessResponse -> {
                    result.data?.let { joke ->
                        repository.local.insertJokes(FoodJokesEntity(joke))
                    }
                }
                is NetworkResponse.ErrorResponse -> {
                    _uiState.update { it.copy(error = result.message) }
                }
                else -> {}
            }
        } catch (e: Exception) {
            Timber.e("Joke failed: ${e.message}")
            _uiState.update { it.copy(error = "Joke failed") }
        } finally {
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    private fun clearError() {
        _uiState.update { it.copy(error = null) }
    }

    private suspend fun cacheRecipes(munchRecipe: MunchRecipe) {
        try {
            repository.local.insertRecipes(RecipeEntity(munchRecipe))
            Timber.d("Cached ${munchRecipe.result?.size} recipes")
        } catch (e: Exception) {
            Timber.e("Caching failed: ${e.message}")
            throw e
        }
    }

    private fun handleRecipesResponse(response: Response<MunchRecipe>) =
        when {
            response.isSuccessful && response.body()?.result?.isNotEmpty() == true -> {
                NetworkResponse.SuccessResponse(response.body()!!)
            }
            response.code() == 402 -> NetworkResponse.ErrorResponse("API Limit")
            else -> NetworkResponse.ErrorResponse("Recipes unavailable")
        }

    private fun handleJokeResponse(response: Response<FoodJokes>) =
        when {
            response.isSuccessful -> NetworkResponse.SuccessResponse(response.body()!!)
            response.code() == 402 -> NetworkResponse.ErrorResponse("API Limit")
            else -> NetworkResponse.ErrorResponse("Joke unavailable")
        }

    private fun hasInternetConnection(): Boolean {
        val cm = getApplication<Application>()
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetwork?.let { network ->
            cm.getNetworkCapabilities(network)?.run {
                hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
            }
        } ?: false
    }

    private fun applyQueries() = mapOf(
        QUERY_NUMBER to DEFAULT_RECIPES_NUMBER,
        QUERY_API_KEY to API_KEY,
        QUERY_TYPE to DEFAULT_MEAL_TYPE,
        QUERY_DIET to DEFAULT_DIET_TYPE,
        QUERY_ADD_RECIPE_INFORMATION to "true",
        QUERY_FILL_INGREDIENTS to "true"
    )

    private fun applySearchQuery(query: String) = mapOf(
        QUERY_SEARCH to query,
        QUERY_NUMBER to DEFAULT_RECIPES_NUMBER,
        QUERY_API_KEY to API_KEY,
        QUERY_ADD_RECIPE_INFORMATION to "true",
        QUERY_FILL_INGREDIENTS to "true"
    )
}