package com.est.munchy.data.network

import com.est.munchy.domain.model.FoodJokes
import com.est.munchy.domain.model.MunchRecipe
import retrofit2.Response
import retrofit2.http.*

interface MunchApi {
        @GET("/recipes/complexSearch")
        suspend fun getRecipes(
                @QueryMap queries: Map<String, String>
        ): Response<MunchRecipe>

        @GET("/recipes/complexSearch")
        suspend fun searchRecipes(
                @QueryMap searchQuery: Map<String, String>
        ): Response<MunchRecipe>

        @GET("food/jokes/random")
        suspend fun getFoodJoke(
                @Query("apiKey") apiKey: String
        ): Response<FoodJokes>
}
