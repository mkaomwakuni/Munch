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

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

/**
 * Represents an individual recipe result from the API.
 *
 * This data class models the detailed information of a single recipe,
 * including its attributes and ingredients.
 *
 * @Parcelize
 */
@Parcelize
data class ModelResult(
    /** Number of likes the recipe has received. */
    @SerializedName("aggregateLikes")
    val aggregateLikes: Int,
    /** Indicates if the recipe is cheap. */
    @SerializedName("cheap")
    val cheap: Boolean,
    /** Indicates if the recipe is dairy-free. */
    @SerializedName("dairyFree")
    val dairyFree: Boolean,
    /** List of ingredients in the recipe. */
    @SerializedName("extendedIngredients")
    val extendedIngredients: @RawValue List<Ingredients>,
    /** Indicates if the recipe is gluten-free. */
    @SerializedName("glutenFree")
    val glutenFree: Boolean,
    /** Unique identifier of the recipe. */
    @SerializedName("id")
    val recipeId: Int,
    /** URL of the recipe's image. */
    @SerializedName("image")
    val image: String,
    /** Time in minutes to prepare the recipe. */
    @SerializedName("readyInMinutes")
    val readyInMinutes: Int,
    /** Name of the recipe's source. */
    @SerializedName("sourceName")
    val sourceName: String?,
    /** URL to the recipe's source. */
    @SerializedName("sourceUrl")
    val sourceUrl: String,
    /** A summary of the recipe. */
    @SerializedName("summary")
    val summary: String,
    /** The title of the recipe. */
    @SerializedName("title")
    val title: String,
    /** Indicates if the recipe is vegan. */
    @SerializedName("vegan")
    val vegan: Boolean,
    /** Indicates if the recipe is vegetarian. */
    @SerializedName("vegetarian")
    val vegetarian: Boolean,
    /** Indicates if the recipe is very healthy. */
    @SerializedName("veryHealthy")
    val veryHealthy: Boolean,
) : Parcelable