package com.gunfer.characters.model

import com.google.gson.annotations.SerializedName

data class LocationDetailModel(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name:String?,

    @SerializedName("type")
    val type:String?,

    @SerializedName("name")
    val dimension:String?,
) {}
