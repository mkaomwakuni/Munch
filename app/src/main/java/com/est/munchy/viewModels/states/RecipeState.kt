package com.est.munchy.viewModels.states

import com.est.munchy.domain.model.MealAndDietType
import com.est.munchy.domain.model.ModelResult
import com.est.munchy.utils.AppConstants

data class RecipesUiState(
    val recipes: List<ModelResult> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val mealAndDietType: MealAndDietType = MealAndDietType(
        selectedMealType = AppConstants.DEFAULT_MEAL_TYPE,
        selectedMealTypeId = 0,
        selectedDietType = AppConstants.DEFAULT_DIET_TYPE,
        selectedDietTypeId = 0
    ),
    val isNetworkAvailable: Boolean = true,
    val networkMessage: String? = null,
    val searchQuery: String = "",
    val selectedRecipe: ModelResult? = null,
)
