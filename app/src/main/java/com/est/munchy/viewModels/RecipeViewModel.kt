/*
 * MIT License
 * 
 * Copyright (c) 2025 Husty9 
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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

    init {
        viewModelScope.launch {
            // Collect meal and diet type preferences
            dataStoreRepository.readMealAndDietType.collect { preferences ->
                _uiState.update { it.copy(mealAndDietType = preferences) }
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
                getRecipeDetails(event.recipeId)
            }
        }
    }
    private fun getRecipeDetails(recipeId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                // First try to get from local database
                repository.local.readRecipes().collect { recipes ->
                    val recipe = recipes.flatMap { it.recipe.result ?: emptyList() }
                        .find { it.recipeId == recipeId }

                    if (recipe != null) {
                        _uiState.update {
                            it.copy(
                                selectedRecipe = recipe,
                                isLoading = false,
                                error = null
                            )
                        }
                    } else {
                        // If not found locally, fetch from API
                        val queries = mapOf("id" to recipeId.toString())
                        val response = repository.remote.getRecipes(queries)
                        if (response.isSuccessful && response.body() != null) {
                            val fetchedRecipe = response.body()?.result?.find { it.recipeId == recipeId }
                            if (fetchedRecipe != null) {
                                _uiState.update {
                                    it.copy(
                                        selectedRecipe = fetchedRecipe,
                                        isLoading = false,
                                        error = null
                                    )
                                }
                            } else {
                                _uiState.update {
                                    it.copy(
                                        isLoading = false,
                                        error = "Recipe not found"
                                    )
                                }
                            }
                        } else {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    error = "Failed to load recipe details"
                                )
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "An unexpected error occurred"
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
}