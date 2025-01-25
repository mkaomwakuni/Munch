package com.est.munchy.data.database.local.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.est.munchy.domain.model.FoodJokes
import com.est.munchy.utils.AppConstants

/**
 * Entity class representing a food joke in the local database.
 *
 * This class maps to the [AppConstants.FOOD_JOKE_TABLE] table in the database.
 */
@Entity(tableName = AppConstants.FOOD_JOKE_TABLE)
data class FoodJokesEntity(
    /**
     * Represents the actual food joke data.
     *
     * This field is embedded, meaning the fields of the [FoodJokes] class will be
     * stored as columns in the [AppConstants.FOOD_JOKE_TABLE] table.
     */
    @Embedded
    var foodJoke: FoodJokes
) {
    /**
     * The primary key for the food joke entity.
     *
     * This is not auto-generated and should be unique.
     */
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}