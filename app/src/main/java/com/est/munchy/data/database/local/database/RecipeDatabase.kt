package com.est.munchy.data.database.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.est.munchy.data.database.local.dao.MunchDao
import com.est.munchy.data.database.local.entities.BookedRecipeEntity
import com.est.munchy.data.database.local.entities.FoodJokesEntity
import com.est.munchy.data.database.local.entities.RecipeEntity

/**
 * Room database class for the Munch app.
 * Defines the database schema and provides access to the DAO.
 */
@Database(
    entities = [
        RecipeEntity::class,
        FoodJokesEntity::class,
        BookedRecipeEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(MunchTypeConverter::class)
abstract class RecipeDatabase : RoomDatabase() {

    /**
     * Provides access to the MunchDao.
     * @return An instance of the MunchDao.
     */
    abstract fun munchDao(): MunchDao
}