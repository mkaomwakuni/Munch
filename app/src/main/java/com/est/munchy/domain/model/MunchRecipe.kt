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
package com.est.munchy.domain.model

import com.google.gson.annotations.SerializedName

/**
 * Represents a collection of recipes received from the API.
 *
 * This data class is used to model the structure of a network response containing
 * a list of recipe results. It contains a single property, `result`, which is a list
 * of [ModelResult] objects.
 *
 * @property result A list of [ModelResult] objects representing individual recipes.
 *                  This property is mapped from the JSON key "results" using the
 *                  [SerializedName] annotation. If the "results" key is not present
 *                  or the value is null in the response, this list will default to null.
 *
 * @constructor Creates a [MunchRecipe] instance with the given list of [result].
 */
data class MunchRecipe(
    /**
     * A list of [ModelResult] objects representing the individual recipes.
     *
     * If the "results" key is not present in the JSON response or is null,
     * this property will be `null`.
     *
     * @SerializedName("results")
     */
    @SerializedName("results")
    val result: List<ModelResult>? = null
)