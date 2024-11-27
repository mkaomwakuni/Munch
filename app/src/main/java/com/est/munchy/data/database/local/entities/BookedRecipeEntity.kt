package com.est.munchy.data.database.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.est.munchy.domain.model.ModelResult
import com.est.munchy.utils.AppConstants

@Entity(tableName = AppConstants.FAVORITES_TABLE)
class BookedRecipeEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var result: ModelResult
)