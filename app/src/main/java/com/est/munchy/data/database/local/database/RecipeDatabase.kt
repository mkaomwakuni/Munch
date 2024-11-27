package com.est.munchy.data.database.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.est.munchy.data.database.local.dao.MunchDao
import com.est.munchy.data.database.local.entities.BookedRecipeEntity
import com.est.munchy.data.database.local.entities.FoodJokeEntity
import com.est.munchy.data.database.local.entities.RecipeEntity

@Database(
    entities = [RecipeEntity::class, FoodJokeEntity::class, BookedRecipeEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(MunchTypeConverter::class)
abstract class RecipeDatabase: RoomDatabase() {

    abstract fun munchDao(): MunchDao

}