package com.est.munchy.data.database.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.est.munchy.data.database.local.entities.BookedRecipeEntity
import com.est.munchy.data.database.local.entities.FoodJokesEntity
import com.est.munchy.data.database.local.entities.RecipeEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for interacting with the Munch database.
 */
@Dao
interface MunchDao {

    /**
     * Inserts a list of recipes into the munch_recipes_table.
     * Existing entries with the same ID will be replaced.
     * @param recipesEntity The RecipeEntity object to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: RecipeEntity)

    /**
     * Inserts a bookmarked recipe into the booked_table.
     * Existing entries with the same ID will be replaced.
     * @param recipesEntity The BookedRecipeEntity object to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookedMarked(recipesEntity: BookedRecipeEntity)

    /**
     * Inserts a food joke into the food_joke_table.
     * Existing entries with the same ID will be replaced.
     * @param foodJokeEntity The FoodJokesEntity object to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodJoke(foodJokeEntity: FoodJokesEntity)

    /**
     * Reads all recipes from the munch_recipes_table, ordered by ID.
     * @return A Flow emitting a list of RecipeEntity objects.
     */
    @Query("SELECT * FROM munch_recipes_table ORDER BY id ASC")
    fun readRecipes(): Flow<List<RecipeEntity>>

    /**
     * Reads all bookmarked recipes from the booked_table, ordered by ID.
     * @return A Flow emitting a list of BookedRecipeEntity objects.
     */
    @Query("SELECT * FROM booked_table ORDER BY id ASC")
    fun readBookedRecipes(): Flow<List<BookedRecipeEntity>>

    /**
     * Reads the food joke from the food_joke_table, ordered by ID.
     * @return A Flow emitting a list of FoodJokesEntity objects.
     */
    @Query("SELECT * FROM food_joke_table ORDER BY id ASC")
    fun readFoodJoke(): Flow<List<FoodJokesEntity>>

    /**
     * Deletes a specific bookmarked recipe from the booked_table.
     * @param bookedRecipeEntity The BookedRecipeEntity object to delete.
     */
    @Delete
    suspend fun deleteBookedRecipe(bookedRecipeEntity: BookedRecipeEntity)

    /**
     * Deletes all bookmarked recipes from the booked_table.
     */
    @Query("DELETE FROM booked_table")
    suspend fun deleteAllBookedRecipe()
}