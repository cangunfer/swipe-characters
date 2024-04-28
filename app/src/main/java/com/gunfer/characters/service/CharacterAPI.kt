package com.gunfer.characters.service

import com.gunfer.characters.model.CharacterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterAPI {
    @GET("character")
    suspend fun getCharacters(): Response<CharacterResponse>

    @GET("character/")
    suspend fun getCharacters(@Query("page") param:Int): Response<CharacterResponse>
}