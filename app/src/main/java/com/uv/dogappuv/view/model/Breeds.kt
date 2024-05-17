package com.uv.dogappuv.view.model

import com.google.gson.annotations.SerializedName

data class BreedsList(
    @SerializedName("message")
    val breed: Map<String, List<String>>

)