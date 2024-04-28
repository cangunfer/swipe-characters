package com.gunfer.characters.model

import com.google.gson.annotations.SerializedName

class CharacterResponse (
        @SerializedName("results")
        val results: MutableList<CharacterModel>,

        @SerializedName("info")
        val info: InfoModel)
