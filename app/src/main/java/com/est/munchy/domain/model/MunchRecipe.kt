package com.est.munchy.domain.model

import com.google.gson.annotations.SerializedName

data class MunchRecipe(
    @SerializedName("response")
    val  result: List<ModelResult>
)