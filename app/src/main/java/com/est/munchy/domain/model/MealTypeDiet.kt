package com.est.munchy.domain.model

/**
 * Represents the user's selected meal type and diet type preferences.
 *
 * This data class holds the user's choices for a meal type (e.g., "main course") and
 * a diet type (e.g., "vegetarian"). It stores both the name and the associated ID for each.
 *
 * @property selectedMealType The name of the selected meal type.
 * @property selectedMealTypeId The ID of the selected meal type.
 * @property selectedDietType The name of the selected diet type.
 * @property selectedDietTypeId The ID of the selected diet type.
 *
 * @constructor Creates a [MealAndDietType] instance with the specified selections.
 */
data class MealAndDietType(
    /** The name of the selected meal type. */
    val selectedMealType: String,
    /** The ID of the selected meal type. */
    val selectedMealTypeId: Int,
    /** The name of the selected diet type. */
    val selectedDietType: String,
    /** The ID of the selected diet type. */
    val selectedDietTypeId: Int
)