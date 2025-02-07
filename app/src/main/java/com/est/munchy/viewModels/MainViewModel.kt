package com.est.munchy.viewModels

import android.app.Application
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
import com.est.munchy.utils.NetworkChecker
import com.est.munchy.utils.NetworkResponse
import com.est.munchy.viewModels.events.MainEvent
import com.est.munchy.viewModels.states.MainUiState
import com.est.munchy.viewModels.states.RecipesUiState
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
    private val networkChecker: NetworkChecker,
    application: Application
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    private val _netState = MutableStateFlow(RecipesUiState())
    val netState: StateFlow<RecipesUiState> = _netState.asStateFlow()

    init {
        observeLastUpdateTime()
        observeNetworkStatus()
        observeDatabase()
    }

    private fun observeDatabase() {
        viewModelScope.launch {
            repository.local.readRecipes().collect { recipes ->
                Timber.d("New recipes from DB: ${recipes.size}")
                _uiState.update {
                    it.copy(recipes = recipes.flatMap { it.recipe.result ?: emptyList() })
                }
            }
            repository.local.readJokes().collect { jokes ->
                Timber.d("New jokes from DB: ${jokes.size}")
                _uiState.update { it.copy(
                    foodJoke = jokes.firstOrNull()?.foodJoke
                ) }
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
                checkForRecipeUpdates(lastUpdateTime)
            }
        }
    }

    suspend fun checkForRecipeUpdates(lastUpdate: Long) {
        val currentTime = System.currentTimeMillis()
        val updateInterval = 24 * 60 * 60 * 1000

        if (currentTime - lastUpdate > updateInterval && _netState.value.isNetworkAvailable){
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
            _uiState.update { state ->
                state.copy(
                    recipes = munchRecipe.result?: emptyList(),
                    error = null
                )
            }
        } catch (e: Exception) {
            Timber.e("Caching failed: ${e.message}")
            _netState.update {
            it.copy(networkMessage = "Failed to cache recipes") }
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

    private fun observeNetworkStatus() {
        viewModelScope.launch {
            networkChecker.getNetworkAvailability().collect { isNetworkAvailable ->
                val wasUnavailable = !_netState.value.isNetworkAvailable
                _netState.update { state ->
                    state.copy(
                        isNetworkAvailable = isNetworkAvailable,
                        networkMessage = when {
                            !isNetworkAvailable -> "No internet connection"
                            wasUnavailable && isNetworkAvailable -> "Back Online"
                            else -> null
                        }
                    )
                }

                // If we're back online, try to refresh recipes
                if (isNetworkAvailable && wasUnavailable) {
                    viewModelScope.launch {
                        checkForRecipeUpdates(0L)
                    }
                }

                // If we're offline, ensure we're showing cached recipes
                if (!isNetworkAvailable) {
                    observeDatabase()
                }
            }
        }
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