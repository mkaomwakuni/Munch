package com.est.munchy.data.database.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.est.munchy.domain.model.ModelResult
import com.est.munchy.utils.AppConstants

/**
 * Data class representing a bookmarked recipe in the database.
 */
@Entity(tableName = AppConstants.FAVORITES_TABLE)
data class BookedRecipeEntity(
    /**
     * Primary key for the entity, auto-generated.
     */
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    /**
     * The recipe data stored as a ModelResult object.
     */
    var result: ModelResult
)