package com.sal7one.musicswitcher

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.sal7one.musicswitcher.repository.DataStoreProvider
import com.sal7one.musicswitcher.utils.MusicHelpers
import com.sal7one.musicswitcher.viewmodels.DeepLinkHandlerViewModel
import com.sal7one.musicswitcher.viewmodels.DeepLinkHandlerViewModelFactory


class DeepLinkHandlerActivity : AppCompatActivity() {

    private lateinit var viewModel: DeepLinkHandlerViewModel
    private lateinit var data: Uri
    private lateinit var dataStoreProvider: DataStoreProvider
    private var action: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataStoreProvider = DataStoreProvider(applicationContext).getInstance()
        viewModel = ViewModelProvider(this, DeepLinkHandlerViewModelFactory(dataStoreProvider))[DeepLinkHandlerViewModel::class.java]

        data = intent?.data!!
        intent?.action.also { action = it }

        // data comes from datastore
        viewModel.chosenProvider.observe(this) {
            viewModel.handleDeepLink(data) // start opening app
        }

        // Instantiate the RequestQueue.

        viewModel.sameApp.observe(this) {
            if (it) {
                val i = Intent(action, data)
                val appPackage = MusicHelpers.getMusicAppPackage(data.toString())
                i.setPackage(appPackage)
                startActivity(i)
            }
        }

        viewModel.differentApp.observe(this) {
            val queue = Volley.newRequestQueue(this)

            if (it) {
                val songURL = data.toString()
                val stringRequest = StringRequest(Request.Method.GET, songURL,

                    { response ->
                        val songName = MusicHelpers.extractFromURL(songURL, response)
                        val searchURL = viewModel.searchLink.value
                        val query: String = Uri.encode(songName, "utf-8")

                        switchToApp(searchURL + query)
                    },
                    {
                        Log.e("DEEP_LINK", "Volley Error")
                    }
                )
                queue.add(stringRequest)
            }
        }
    }

    private fun switchToApp(songName: String) {
        val uri = Uri.parse(songName)
        val i = Intent(Intent.ACTION_VIEW, uri)
        i.setPackage(viewModel.musicPackage.value)
        startActivity(i)
    }

    override fun onStop() {
        super.onStop()
        finishAndRemoveTask()
    }
}