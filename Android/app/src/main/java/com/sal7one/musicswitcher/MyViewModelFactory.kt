package com.sal7one.musicswitcher

import DataStoreProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MyViewModelFactory(private val dataStoreProvider: DataStoreProvider) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(ApplicationViewModel::class.java)) {
                        return ApplicationViewModel(dataStoreProvider) as T
                }
                throw IllegalStateException()
        }
}