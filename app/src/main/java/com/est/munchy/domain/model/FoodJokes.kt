package com.est.munchy.domain.model

import com.google.gson.annotations.SerializedName
/**
 * Represents a food joke fetched from the API.
 *
 * This data class is used to model the structure of a food joke received in a
 * network response. It contains a single property, `text`, which holds the
 * content of the food joke.
 *
 * @property text The content of the food joke. It is mapped from the JSON key "text"
 *                 using the [SerializedName] annotation.
 *
 * @constructor Creates a [FoodJokes] instance with the given [text].
 */
data class FoodJokes(
    /**
     * The text of the food joke.
     *
     * This property holds the actual joke content.
     *
     * @SerializedName("text")
     */
    @SerializedName("text")
    val text: String
)