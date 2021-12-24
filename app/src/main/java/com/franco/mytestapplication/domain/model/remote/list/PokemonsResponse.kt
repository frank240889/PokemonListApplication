package com.franco.mytestapplication.domain.model.remote.list


import com.google.gson.annotations.SerializedName

data class PokemonsResponse(
    @SerializedName("count")
    val count: Int? = 0,
    @SerializedName("next")
    val next: String? = "",
    @SerializedName("previous")
    val previous: Any? = Any(),
    @SerializedName("results")
    val results: List<Result>? = listOf()
)