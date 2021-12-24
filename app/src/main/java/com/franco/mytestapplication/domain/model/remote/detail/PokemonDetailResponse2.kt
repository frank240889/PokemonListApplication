package com.franco.mytestapplication.domain.model.remote.detail


import com.google.gson.annotations.SerializedName

data class PokemonDetailResponse2(
    @SerializedName("abilities")
    val abilities: List<Ability>? = emptyList(),
    @SerializedName("sprites")
    val sprites: Sprites? = Sprites(),
    @SerializedName("types")
    val types: List<Type>? = emptyList()
)