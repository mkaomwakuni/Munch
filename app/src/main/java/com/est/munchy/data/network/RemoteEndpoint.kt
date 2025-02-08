/*
 * MIT License
 * 
 * Copyright (c) 2025 Husty9 
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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
