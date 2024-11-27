package com.est.munchy.data.database.local.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.est.munchy.domain.model.FoodJokes
import com.est.munchy.utils.AppConstants

@Entity(tableName = AppConstants.Companion.FOOD_JOKE_TABLE)
class FoodJokeEntity (
    @Embedded
    var foodJoke: FoodJokes
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}