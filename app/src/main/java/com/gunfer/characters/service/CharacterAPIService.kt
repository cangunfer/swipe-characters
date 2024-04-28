package com.gunfer.characters.service

import com.gunfer.characters.model.CharacterResponse
import com.gunfer.characters.util.Constants.BASE_URL
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CharacterAPIService {

    //"characters": "https://rickandmortyapi.com/api/character"

    private val characterAPI = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CharacterAPI::class.java)


    suspend fun getData(): Response<CharacterResponse> {
        return characterAPI.getCharacters()
    }

    suspend fun getPageData(param: Int): Response<CharacterResponse> {
        return characterAPI.getCharacters(param)
    }
}