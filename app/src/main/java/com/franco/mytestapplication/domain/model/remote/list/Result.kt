package com.franco.mytestapplication.domain.model.remote.list


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("url")
    val url: String? = ""
)