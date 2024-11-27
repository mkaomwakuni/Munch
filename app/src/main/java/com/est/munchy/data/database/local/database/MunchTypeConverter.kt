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
    fun fromMunchRecipeToString(munchRecipe: MunchRecipe): String {
        return gson.toJson(munchRecipe)
    }

    @TypeConverter
    fun fromStringToMunchRecipe(data: String): MunchRecipe {
        val listType = object : TypeToken<MunchRecipe>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun fromResultToString(result: ModelResult): String {
        return gson.toJson(result)
    }

    @TypeConverter
    fun fromStringToResult(data: String): ModelResult {
        val listType = object : TypeToken<ModelResult>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun fromFoodJokeToString(foodJoke: FoodJokes): String {
        return gson.toJson(foodJoke)
    }

    @TypeConverter
    fun fromStringToFoodJoke(data: String): FoodJokes {
        val listType = object : TypeToken<FoodJokes>() {}.type
        return gson.fromJson(data, listType)
    }
}