package com.sal7one.musicswitcher

import DateStoreProvider
import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect

class MainActvityViewModel(application: Application) : AndroidViewModel(application) {

    private val dataStoreManager = DateStoreProvider(this.getApplication())
    var choosen_Provider  = MutableLiveData<String>()

    init {
        choosen_Provider.value = "spotify"
        getData()
    }

    fun getData() = viewModelScope.launch(Dispatchers.IO) {
        dataStoreManager.getFromDataStore().catch { e ->
            e.printStackTrace()
        }.collect {
            choosen_Provider.postValue(it)
        }
    }

    fun SaveData(userchoice : String) = viewModelScope.launch(Dispatchers.IO) {
        dataStoreManager.savetoDataStore(userchoice)
    }
}