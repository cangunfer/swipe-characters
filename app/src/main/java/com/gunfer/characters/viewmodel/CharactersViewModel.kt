package com.gunfer.characters.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.gunfer.characters.model.CharacterResponse
import com.gunfer.characters.service.CharacterAPIService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharactersViewModel(application: Application) : BaseViewModel(application) {
    val characterResponseLiveData= MutableLiveData<CharacterResponse>()
    private val characterAPIService = CharacterAPIService()

    init {
        refreshData()
    }

    fun refreshData() {
        getDataFromAPI()
    }

    fun getDataFromAPI() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                var response = characterAPIService.getData()
                response.body()?.let {
                     characterResponseLiveData.postValue(it)
                }
            } catch (e: java.lang.Exception) {
                println(e.message)
            } finally {
            }
        }
    }

    fun getDataFromAPI(pageUrl: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                var response = characterAPIService.getPageData(pageUrl)
                response.body()?.let {
                    characterResponseLiveData.postValue(it)
                }
            } catch (e: java.lang.Exception) {
                println(e.message)
            } finally {
            }
        }
    }
}