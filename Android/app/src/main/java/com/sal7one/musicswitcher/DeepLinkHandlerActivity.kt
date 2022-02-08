package com.sal7one.musicswitcher

import com.sal7one.musicswitcher.repository.DataStoreProvider
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.sal7one.musicswitcher.controllers.MyViewModelFactory
import com.sal7one.musicswitcher.controllers.ApplicationViewModel
import com.sal7one.musicswitcher.utils.Constants
import com.sal7one.musicswitcher.utils.SongExtractor


class DeepLinkHandlerActivity : AppCompatActivity() {

    private lateinit var viewModel: ApplicationViewModel
    private lateinit var data: Uri
    private lateinit var dataStoreProvider: DataStoreProvider
    private val activityTAG = "DeepLinkHandlerActivity"
    private var action : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataStoreProvider = DataStoreProvider(applicationContext).getInstance()

        viewModel = ViewModelProvider(this, MyViewModelFactory(dataStoreProvider)).get(
            ApplicationViewModel::class.java
        )

        data = intent?.data!!
        intent?.action.also { action = it }

        //viewModel.

        viewModel.handleDeepLink(data)
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)

        viewModel.sameApp.observe(this, {
            if (it) {
                val i = Intent(action, data)
                val appPackage = sameAppPackage(data.toString())
                i.setPackage(appPackage)
                startActivity(i)
            }
        })

        viewModel.differentApp.observe(this, {
            if (it) {
                val songURL = data.toString()
                val stringRequest = StringRequest(Request.Method.GET, songURL,

                    { response ->
                        val songName = SongExtractor.extractFromURL(songURL, response)
                        val searchURL = viewModel.searchLink.value
                        val query: String = Uri.encode(songName, "utf-8")

                        switchToApp(searchURL + query)
                    },
                    {
                        Log.d(activityTAG, "Volley Error")
                    }
                )
                queue.add(stringRequest)
            }
        })
    }

    private fun switchToApp(songName: String) {

        val uri = Uri.parse(songName)
        val i = Intent(Intent.ACTION_VIEW, uri)
        i.setPackage(viewModel.musicPackage.value)
        startActivity(i)
    }

    private fun sameAppPackage(currentLink: String): String {
        when {
            currentLink.contains(Constants.SPOTIFY.link) -> {
                return Constants.SPOTIFY_PACKAGE.link
            }
            currentLink.contains(Constants.APPLE_MUSIC.link) -> {
                return Constants.APPLE_MUSIC_PACKAGE.link
            }
            currentLink.contains(Constants.ANGHAMI.link) -> {
                return Constants.ANGHAMI_PACKAGE.link
            }
            currentLink.contains(Constants.DEEZER.link) -> {
                return Constants.DEEZER_PACKAGE.link
            }
            currentLink.contains(Constants.YT_MUSIC.link) -> {
                return Constants.YT_MUSIC_PACKAGE.link
            }
            else -> return Constants.SPOTIFY_PACKAGE.link
        }
    }

    override fun onStop() {
        super.onStop()
        finishAndRemoveTask()
    }
}