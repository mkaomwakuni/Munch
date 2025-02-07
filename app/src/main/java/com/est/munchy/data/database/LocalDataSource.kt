package com.est.munchy.data.database

import com.est.munchy.data.database.local.dao.MunchDao
import com.est.munchy.data.database.local.entities.BookedRecipeEntity
import com.est.munchy.data.database.local.entities.FoodJokesEntity
import com.est.munchy.data.database.local.entities.RecipeEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Data source for accessing local data through the MunchDao.
 */
class LocalDataSource @Inject constructor(
    private val munchDao: MunchDao
) {

    /**
     * Reads all recipes from the local database.
     * @return A Flow emitting a list of RecipeEntity objects.
     */
    fun readRecipes(): Flow<List<RecipeEntity>> {
        return munchDao.readRecipes()
    }

    /**
     * Reads all bookmarked recipes from the local database.
     * @return A Flow emitting a list of BookedRecipeEntity objects.
     */
    fun readBooked(): Flow<List<BookedRecipeEntity>> {
        return munchDao.readBookedRecipes()
    }

    /**
     * Reads food jokes from the local database.
     * @return A Flow emitting a list of FoodJokesEntity objects.
     */
    fun readJokes(): Flow<List<FoodJokesEntity>> {
        return munchDao.readFoodJoke()
    }

    /**
     * Inserts a recipe into the local database.
     * @param recipeEntity The RecipeEntity object to insert.
     */
    suspend fun insertRecipes(recipeEntity: RecipeEntity) {
        munchDao.insertRecipe(recipeEntity)
    }

    /**
     * Inserts a bookmarked recipe into the local database.
     * @param bookedRecipeEntity The BookedRecipeEntity object to insert.
     */
    suspend fun insertBooked(bookedRecipeEntity: BookedRecipeEntity) {
        munchDao.insertBookedMarked(bookedRecipeEntity)
    }

    /**
     * Inserts a food joke into the local database.
     * @param foodJokeEntity The FoodJokesEntity object to insert.
     */
    suspend fun insertJokes(foodJokeEntity: FoodJokesEntity) {
        munchDao.insertFoodJoke(foodJokeEntity)
    }

    /**
     * Deletes a bookmarked recipe from the local database.
     * @param bookedRecipeEntity The BookedRecipeEntity object to delete.
     */
    suspend fun deleteBookedRecipe(bookedRecipeEntity: BookedRecipeEntity) {
        munchDao.deleteBookedRecipe(bookedRecipeEntity)
    }

    /**
     * Deletes all bookmarked recipes from the local database.
     */
    suspend fun deleteAllBooked() {
        munchDao.deleteAllBookedRecipe()
    }
}