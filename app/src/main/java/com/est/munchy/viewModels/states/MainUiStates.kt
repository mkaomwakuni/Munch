package com.est.munchy.viewModels.states

import com.est.munchy.data.database.local.entities.BookedRecipeEntity
import com.est.munchy.domain.model.FoodJokes
import com.est.munchy.domain.model.ModelResult

data class MainUiState(
    val recipes: List<ModelResult> = emptyList(),
    val favoriteRecipes: List<BookedRecipeEntity> = emptyList(),
    val foodJoke: FoodJokes? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = "",
    val isRefreshing: Boolean = false
)