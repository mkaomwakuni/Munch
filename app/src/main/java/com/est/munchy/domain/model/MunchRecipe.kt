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