package com.est.munchy.viewModels.events

import com.est.munchy.data.database.local.entities.BookedRecipeEntity
import com.est.munchy.domain.model.ModelResult

sealed class RecipesEvent {
    data class UpdateMealAndDietType(
        val mealType: String,
        val mealTypeId: Int,
        val dietType: String,
        val dietTypeId: Int
    ) : RecipesEvent()
    object RefreshRecipes : RecipesEvent()
    data class SearchRecipes(val query: String) : RecipesEvent()
    object ClearSearch : RecipesEvent()
    data class GetRecipeDetails(val recipeId: Int) : RecipesEvent()
}

sealed class MainEvent {
    data class SearchRecipes(val query: String) : MainEvent()
    object RefreshRecipes : MainEvent()
    data class AddToFavorites(val recipe: ModelResult) : MainEvent()
    data class RemoveFromFavorites(val recipe: BookedRecipeEntity) : MainEvent()
    object GetFoodJoke : MainEvent()
    object ClearError : MainEvent()
}