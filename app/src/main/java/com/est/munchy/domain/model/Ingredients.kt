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

/**
 * Represents an ingredient in a recipe.
 *
 * This data class models the details of a single ingredient, including its name,
 * quantity, and other relevant properties.
 *
 * @Parcelize
 */
@Parcelize
data class Ingredients(
    /** The amount of the ingredient. */
    @SerializedName("amount")
    val amount: Double,
    /** The consistency of the ingredient (e.g., "solid", "liquid"). */
    @SerializedName("consistency")
    val consistency: String,
    /** URL to an image of the ingredient. */
    @SerializedName("image")
    val image: String,
    /** The name of the ingredient. */
    @SerializedName("name")
    val name: String,
    /** The original description of the ingredient. */
    @SerializedName("original")
    val original: String,
    /** The unit of measurement for the ingredient (e.g., "cup", "g"). */
    @SerializedName("unit")
    val unit: String
) : Parcelable