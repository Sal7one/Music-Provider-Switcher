package com.sal7one.musicswitcher.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sal7one.musicswitcher.repository.DataStoreProvider

class DeepLinkHandlerViewModelFactory(private val dataStoreProvider: DataStoreProvider) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DeepLinkHandlerViewModel::class.java)) {
            return DeepLinkHandlerViewModel(dataStoreProvider) as T
        }
        throw IllegalStateException()
    }
}