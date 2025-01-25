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