package com.sal7one.musicswitcher.controllers

import com.sal7one.musicswitcher.repository.DataStoreProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class OptionsViewModelFactory(private val dataStoreProvider: DataStoreProvider) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OptionsViewModel::class.java)) {
            return OptionsViewModel(dataStoreProvider) as T
        }
        throw IllegalStateException()
    }
}