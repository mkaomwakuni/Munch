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
package com.est.munchy.data.database.local.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.est.munchy.domain.model.FoodJokes
import com.est.munchy.utils.AppConstants

/**
 * Entity class representing a food joke in the local database.
 *
 * This class maps to the [AppConstants.FOOD_JOKE_TABLE] table in the database.
 */
@Entity(tableName = AppConstants.FOOD_JOKE_TABLE)
data class FoodJokesEntity(
    /**
     * Represents the actual food joke data.
     *
     * This field is embedded, meaning the fields of the [FoodJokes] class will be
     * stored as columns in the [AppConstants.FOOD_JOKE_TABLE] table.
     */
    @Embedded
    var foodJoke: FoodJokes
) {
    /**
     * The primary key for the food joke entity.
     *
     * This is not auto-generated and should be unique.
     */
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}