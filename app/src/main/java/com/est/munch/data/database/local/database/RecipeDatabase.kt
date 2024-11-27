package com.est.munch.data.database.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.est.munch.data.database.local.dao.MunchDao
import com.est.munch.data.database.local.database.MunchTypeConverter
import com.est.munch.data.database.local.entities.FoodJokeEntity
import com.est.munch.data.database.local.entities.RecipeEntity

@Database(
    entities = [RecipeEntity::class, BookededRecipeEntity::class, FoodJokeEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(MunchTypeConverter::class)
abstract class RecipeDatabase: RoomDatabase() {

    abstract fun munchDao(): MunchDao

}