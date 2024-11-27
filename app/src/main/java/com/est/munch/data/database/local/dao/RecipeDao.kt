package com.est.munch.data.database.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.est.munch.data.database.local.entities.BookedRecipeEntity
import com.est.munch.data.database.local.entities.FoodJokeEntity
import com.est.munch.data.database.local.entities.RecipeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MunchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipesEntity: RecipeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookedMarked(recipesEntity: BookedRecipeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodJoke(foodJokeEntity: FoodJokeEntity)

    @Query("SELECT * FROM munch_recipes_table ORDER BY id ASC")
    fun readRecipes(): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM booked_table ORDER BY id ASC")
    fun readBookedRecipes(): Flow<List<BookedRecipeEntity>>

    @Query("SELECT * FROM food_joke_table ORDER BY id ASC")
    fun readFoodJoke(): Flow<List<FoodJokeEntity>>

    @Delete
    suspend fun deleteBookedRecipe(bookedRecipeEntity: BookedRecipeEntity)

    @Query("DELETE FROM booked_table")
    suspend fun deleteAllBookedRecipe()
}