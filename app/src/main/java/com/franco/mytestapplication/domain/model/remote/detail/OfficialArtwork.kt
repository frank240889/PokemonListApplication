package com.franco.mytestapplication.domain.model.remote.detail


import com.google.gson.annotations.SerializedName

data class OfficialArtwork(
    @SerializedName("front_default")
    val frontDefault: String? = ""
)