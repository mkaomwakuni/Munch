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
package com.est.munchy.data.database.local.database

import androidx.room.TypeConverter
import com.est.munchy.domain.model.FoodJokes
import com.est.munchy.domain.model.ModelResult
import com.est.munchy.domain.model.MunchRecipe
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Type converter class for Room database.
 * Handles conversion between complex data types and their string representations.
 */
class MunchTypeConverter {

    private val gson = Gson()

    /**
     * Converts a ModelResult object to a JSON string.
     * @param modelResult The ModelResult object to convert.
     * @return The JSON string representation of the ModelResult.
     */
    @TypeConverter
    fun fromModelResult(modelResult: ModelResult): String {
        return gson.toJson(modelResult)
    }

    /**
     * Converts a JSON string to a ModelResult object.
     * @param json The JSON string to convert.
     * @return The ModelResult object parsed from the JSON string.
     */
    @TypeConverter
    fun toModelResult(json: String): ModelResult {
        val type = object : TypeToken<ModelResult>() {}.type
        return gson.fromJson(json, type)
    }

    /**
     * Converts a MunchRecipe object to a JSON string.
     * @param munchRecipe The MunchRecipe object to convert.
     * @return The JSON string representation of the MunchRecipe.
     */
    @TypeConverter
    fun fromMunchRecipe(munchRecipe: MunchRecipe): String {
        return gson.toJson(munchRecipe)
    }

    /**
     * Converts a JSON string to a MunchRecipe object.
     * @param json The JSON string to convert.
     * @return The MunchRecipe object parsed from the JSON string.
     */
    @TypeConverter
    fun toMunchRecipe(json: String): MunchRecipe {
        val type = object : TypeToken<MunchRecipe>() {}.type
        return gson.fromJson(json, type)
    }

    /**
     * Converts a FoodJokes object to a JSON string.
     * @param foodJokes The FoodJokes object to convert.
     * @return The JSON string representation of the FoodJokes.
     */
    @TypeConverter
    fun fromFoodJokes(foodJokes: FoodJokes): String {
        return gson.toJson(foodJokes)
    }

    /**
     * Converts a JSON string to a FoodJokes object.
     * @param json The JSON string to convert.
     * @return The FoodJokes object parsed from the JSON string.
     */
    @TypeConverter
    fun toFoodJokes(json: String): FoodJokes {
        val type = object : TypeToken<FoodJokes>() {}.type
        return gson.fromJson(json, type)
    }
}