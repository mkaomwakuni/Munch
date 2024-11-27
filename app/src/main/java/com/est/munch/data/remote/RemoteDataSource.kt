package com.est.munch.data.remote

import com.est.munch.data.network.MunchApi
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val munchApi: MunchApi
) {

    suspend fun getRecipes(queries: Map<String, String>): Response<MunchRecipe> {
        return munchApi.getRecipes(queries)
    }

    suspend fun searchRecipes(searchQuery: Map<String, String>): Response<MunchRecipe> {
        return munchApi.searchRecipes(searchQuery)
    }

    suspend fun getFoodJoke(apiKey: String): Response<FoodJoke> {
        return munchApi.getFoodJoke(apiKey)
    }
}