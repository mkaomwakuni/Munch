package com.est.munch.data.database.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.est.munch.utils.AppConstants

@Entity(tableName = AppConstants.Companion.RECIPES_TABLE)
class RecipeEntity (
    val recipe: MunchRecipe
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}