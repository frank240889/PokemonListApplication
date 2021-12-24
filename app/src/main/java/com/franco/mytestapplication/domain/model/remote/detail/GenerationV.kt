package com.franco.mytestapplication.domain.model.remote.detail


import com.google.gson.annotations.SerializedName

data class GenerationV(
    @SerializedName("black-white")
    val blackWhite: BlackWhite? = BlackWhite()
)