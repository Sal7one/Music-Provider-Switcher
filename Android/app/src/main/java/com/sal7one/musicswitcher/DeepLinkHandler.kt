package com.sal7one.musicswitcher


import DataStoreProvider
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider




class DeepLinkHandler : AppCompatActivity() {

    private lateinit var viewModel: MainActvityViewModel
    private var choosen_Provider = ""
    private lateinit var data: Uri
    private lateinit var dataStoreProvider: DataStoreProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataStoreProvider = DataStoreProvider.getInstance(this)

        viewModel = ViewModelProvider(this, MyViewModelFactory(dataStoreProvider)).get(MainActvityViewModel::class.java)
        data = intent?.data!!

        var action = intent?.action // Action to play music TODO analyze
        viewModel.handleDeepLink(data)

       viewModel.sameApp.observe(this, {

           if(it){
               Log.d("MUSICMEE",data.toString())
               Log.d("MUSICMEE",viewModel.Musicpackage.value.toString())
               val i = Intent()
               i.setPackage(viewModel.Musicpackage.value) // viewmodel based on prefrance
               i.setAction(action)
               i.setData(data)
              startActivity(i)
           }

        })

    }
}