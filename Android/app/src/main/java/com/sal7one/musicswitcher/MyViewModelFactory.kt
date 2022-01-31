package com.sal7one.musicswitcher

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MyViewModelFactory(application : Application) : ViewModelProvider.Factory{
        private var  mApplication = application;
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainActvityViewModel(mApplication) as T
        }

}
