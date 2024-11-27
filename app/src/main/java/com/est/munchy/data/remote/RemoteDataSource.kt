package com.est.munchy.data.remote

import com.est.munchy.data.network.MunchApi
import com.est.munchy.domain.model.FoodJokes
import com.est.munchy.domain.model.MunchRecipe
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

    suspend fun getFoodJoke(apiKey: String): Response<FoodJokes> {
        return munchApi.getFoodJoke(apiKey)
    }
}