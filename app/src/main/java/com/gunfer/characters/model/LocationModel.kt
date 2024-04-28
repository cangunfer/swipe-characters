package com.gunfer.characters.model

import com.google.gson.annotations.SerializedName

data class LocationModel(
    @SerializedName("name")
    val name: String?,

    @SerializedName("url")
    val url:String?,
    ) {}