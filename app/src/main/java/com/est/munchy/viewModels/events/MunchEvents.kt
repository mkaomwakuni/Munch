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