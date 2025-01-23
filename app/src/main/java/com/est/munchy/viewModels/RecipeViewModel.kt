package com.est.munchy.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.est.munchy.data.DataStoreRepository
import com.est.munchy.data.Repository
import com.est.munchy.utils.NetworkChecker
import com.est.munchy.viewModels.events.RecipesEvent
import com.est.munchy.viewModels.states.RecipesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for managing recipe-related data and user preferences.
 *
 * @property dataStoreRepository Repository for accessing and modifying DataStore preferences.
 * @property networkChecker Utility for checking network connectivity.
 * @property application The application context.
 */
@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val networkChecker: NetworkChecker,
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    /**
     * The internal mutable state flow for the UI state.
     */
    private val _uiState = MutableStateFlow(RecipesUiState())

    /**
     * The public immutable state flow for the UI state.
     */
    val uiState: StateFlow<RecipesUiState> = _uiState.asStateFlow()

    /**
     * Indicates the current network status.
     */
    var networkStatus = false

    /**
     * Indicates if the device was previously offline and is now back online.
     */
    var backOnline = false

    init {
        viewModelScope.launch {
            // Collect meal and diet type preferences
            dataStoreRepository.readMealAndDietType.collect { preferences ->
                _uiState.update { it.copy(mealAndDietType = preferences) }
            }

            // Monitor network availability
            networkChecker.getNetworkAvailability().collect { isOnline ->
                networkStatus = isOnline
                showNetworkStatus()
            }
        }
    }

    /**
     * Handles various events triggered by the UI.
     *
     * @param event The RecipesEvent to be processed.
     */
    fun onEvent(event: RecipesEvent) {
        when (event) {
            is RecipesEvent.UpdateMealAndDietType -> {
                saveMealAndDietType(
                    event.mealType,
                    event.mealTypeId,
                    event.dietType,
                    event.dietTypeId
                )
            }
            is RecipesEvent.SearchRecipes -> {
                _uiState.update { it.copy(searchQuery = event.query) }
            }
            is RecipesEvent.RefreshRecipes -> {

            }
            RecipesEvent.ClearSearch -> {
                _uiState.update { it.copy(searchQuery = "") }
            }

            is RecipesEvent.GetRecipeDetails -> {
                        val selectedRecipe = uiState.value.recipes.find { it.recipeId == event.recipeId }
                        _uiState.update { currentState ->
                            currentState.copy(
                                selectedRecipe = selectedRecipe,
                                isLoading = false
                            )
                        }
            }
        }
    }
    private fun getRecipeDetails(recipeId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            repository.local.readRecipes().collect { recipes ->
                val recipe = recipes.flatMap { it.recipe.result ?: emptyList() }
                    .find { it.recipeId == recipeId }
                _uiState.update {
                    it.copy(
                        selectedRecipe = recipe,
                        isLoading = false
                    )
                }
            }
        }
    }

    /**
     * Saves the user's meal and diet type preferences to DataStore.
     *
     * @param mealType The selected meal type.
     * @param mealTypeId The ID of the selected meal type.
     * @param dietType The selected diet type.
     * @param dietTypeId The ID of the selected diet type.
     */
    private fun saveMealAndDietType(
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dietTypeId: Int
    ) = viewModelScope.launch(Dispatchers.IO) {
        dataStoreRepository.saveMealAndDietType(
            mealType,
            mealTypeId,
            dietType,
            dietTypeId
        )
    }

    /**
     * Saves the back online status to DataStore.
     *
     * @param backOnline Boolean indicating if the device is back online.
     */
    private fun saveBackOnline(backOnline: Boolean) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveBackOnline(backOnline)
        }

    /**
     * Updates the UI state based on the current network status.
     */
    private fun showNetworkStatus() {
        if (!networkStatus) {
            _uiState.update { it.copy(
                error = "No Internet Connection",
                wasOffline = true
            ) }
            saveBackOnline(true)
        } else if (networkStatus) {
            if (backOnline) {
                _uiState.update { it.copy(
                    error = "We're back online.",
                    wasOffline = false
                ) }
                saveBackOnline(false)
            }
        }
    }
}