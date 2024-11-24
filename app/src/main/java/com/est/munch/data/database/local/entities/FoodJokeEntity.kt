package com.est.munch.data.database.local.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.est.munch.utils.AppConstants

@Entity(tableName = AppConstants.Companion.FOOD_JOKE_TABLE)
class FoodJokeEntity (
    @Embedded
    var foodJoke: FoodJoke
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}