package com.est.munchy.data.database.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.est.munchy.domain.model.MunchRecipe
import com.est.munchy.utils.AppConstants

@Entity(tableName = AppConstants.Companion.RECIPES_TABLE)
data class RecipeEntity (
    val recipe: MunchRecipe
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}