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