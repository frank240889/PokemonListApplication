package com.franco.mytestapplication.domain.model.remote.detail


import com.google.gson.annotations.SerializedName

data class OmegarubyAlphasapphire(
    @SerializedName("front_default")
    val frontDefault: String? = "",
    @SerializedName("front_female")
    val frontFemale: Any? = Any(),
    @SerializedName("front_shiny")
    val frontShiny: String? = "",
    @SerializedName("front_shiny_female")
    val frontShinyFemale: Any? = Any()
)