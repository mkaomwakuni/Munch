package com.est.munchy.data.database.local.database

import androidx.room.TypeConverter
import com.est.munchy.domain.model.FoodJokes
import com.est.munchy.domain.model.ModelResult
import com.est.munchy.domain.model.MunchRecipe
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MunchTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromModelResult(modelResult: ModelResult): String {
        return gson.toJson(modelResult)
    }

    @TypeConverter
    fun toModelResult(json: String): ModelResult {
        val type = object : TypeToken<ModelResult>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun fromMunchRecipe(munchRecipe: MunchRecipe): String {
        return gson.toJson(munchRecipe)
    }

    @TypeConverter
    fun toMunchRecipe(json: String): MunchRecipe {
        val type = object : TypeToken<MunchRecipe>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun fromFoodJokes(foodJokes: FoodJokes): String {
        return gson.toJson(foodJokes)
    }

    @TypeConverter
    fun toFoodJokes(json: String): FoodJokes {
        val type = object : TypeToken<FoodJokes>() {}.type
        return gson.fromJson(json, type)
    }
}