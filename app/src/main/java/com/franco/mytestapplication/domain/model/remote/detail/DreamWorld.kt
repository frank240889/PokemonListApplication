package com.franco.mytestapplication.domain.model.remote.detail


import com.google.gson.annotations.SerializedName

data class DreamWorld(
    @SerializedName("front_default")
    val frontDefault: String? = "",
    @SerializedName("front_female")
    val frontFemale: Any? = Any()
)