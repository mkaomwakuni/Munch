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