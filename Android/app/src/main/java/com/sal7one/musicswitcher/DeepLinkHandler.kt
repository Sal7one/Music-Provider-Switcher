package com.sal7one.musicswitcher


import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider

class DeepLinkHandler : AppCompatActivity() {

    private lateinit var viewModel: MainActvityViewModel
    private var choosen_Provider = ""
    private lateinit var data: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, MyViewModelFactory(this.getApplication())).get(MainActvityViewModel::class.java)
        data = intent?.data!!
        viewModel.handleDeepLink(data)

    }
}