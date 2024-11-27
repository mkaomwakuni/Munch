package com.est.munchy.data.database

import com.est.munchy.data.database.local.dao.MunchDao
import com.est.munchy.data.database.local.entities.BookedRecipeEntity
import com.est.munchy.data.database.local.entities.FoodJokeEntity
import com.est.munchy.data.database.local.entities.RecipeEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val munchDao: MunchDao
){

    fun readRecipes(): Flow<List<RecipeEntity>> {
        return munchDao.readRecipes()
    }

    fun readBooked() : Flow<List<BookedRecipeEntity>> {
        return munchDao.readBookedRecipes()
    }

    fun readJokes() : Flow<List<FoodJokeEntity>> {
        return  munchDao.readFoodJoke()
    }

    suspend fun insertRecipes(recipeEntity: RecipeEntity) {
        munchDao.insertRecipes(recipeEntity)
    }

    suspend fun insertBooked(bookedRecipeEntity: BookedRecipeEntity) {
        munchDao.insertBookedMarked(bookedRecipeEntity)
    }

    suspend fun insertJokes(foodJokeEntity: FoodJokeEntity) {
        munchDao.insertFoodJoke(foodJokeEntity)
    }

    suspend fun deleteBookedRecipe(bookedRecipeEntity: BookedRecipeEntity) {
        munchDao.deleteBookedRecipe(bookedRecipeEntity)
    }

    suspend fun deleteAllBooked() {
        munchDao.deleteAllBookedRecipe()
    }
}