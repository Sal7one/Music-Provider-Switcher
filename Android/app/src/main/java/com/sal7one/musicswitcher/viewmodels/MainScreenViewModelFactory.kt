package com.sal7one.musicswitcher.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sal7one.musicswitcher.repository.DataStoreProvider

@Suppress("UNCHECKED_CAST")
class MainScreenViewModelFactory(private val dataStoreProvider: DataStoreProvider) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainScreenViewModel::class.java)) {
            return MainScreenViewModel(dataStoreProvider) as T
        }
        throw IllegalStateException()
    }
}