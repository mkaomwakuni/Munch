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
package com.est.munchy.data.remote

import com.est.munchy.data.network.MunchApi
import com.est.munchy.domain.model.FoodJokes
import com.est.munchy.domain.model.MunchRecipe
import retrofit2.Response
import javax.inject.Inject

/**
 * [RemoteDataSource] handles all network requests related to fetching data from the Munch API.
 *
 * This class acts as the primary source for retrieving data from the remote server. It uses the
 * [MunchApi] interface to interact with the network and retrieve responses. All methods in this
 * class are `suspend` functions, meaning they are designed to be called within coroutines.
 *
 * @property munchApi An instance of [MunchApi] which is used to perform the network requests.
 *                    This is dependency injected into the class.
 *
 * @constructor Creates a [RemoteDataSource] instance.
 * @param munchApi The [MunchApi] instance for making API calls. Injected by Hilt.
 */
class RemoteDataSource @Inject constructor(
    private val munchApi: MunchApi
) {

    /**
     * Retrieves a list of recipes from the remote server based on the provided queries.
     *
     * This function communicates with the [MunchApi] to fetch recipe data.
     *
     * @param queries A map of query parameters to be included in the request. These can be used to
     *                filter or modify the results of the recipe search.
     * @return A [Response] object containing a [MunchRecipe] if the request was successful,
     *         or an error response if it failed.
     * @see MunchApi.getRecipes
     */
    suspend fun getRecipes(queries: Map<String, String>): Response<MunchRecipe> {
        return munchApi.getRecipes(queries)
    }

    /**
     * Searches for recipes based on a given search query.
     *
     * This function calls [MunchApi.searchRecipes] to perform the search.
     *
     * @param searchQuery A map of query parameters that define the search criteria.
     * @return A [Response] object with [MunchRecipe] if successful.
     * @see MunchApi.searchRecipes
     */
    suspend fun searchRecipes(searchQuery: Map<String, String>): Response<MunchRecipe> {
        return munchApi.searchRecipes(searchQuery)
    }

    /**
     * Fetches a random food joke from the API.
     *
     * This function uses the [MunchApi] to retrieve the food joke.
     *
     * @param apiKey The API key required to make this specific request.
     * @return A [Response] containing a [FoodJokes] object if the request was successful.
     * @see MunchApi.getFoodJoke
     */
    suspend fun getFoodJoke(apiKey: String): Response<FoodJokes> {
        return munchApi.getFoodJoke(apiKey)
    }
}