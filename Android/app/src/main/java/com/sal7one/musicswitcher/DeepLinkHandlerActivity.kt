package com.sal7one.musicswitcher

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.Intent.FLAG_ACTIVITY_REQUIRE_NON_BROWSER
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.sal7one.musicswitcher.compose.ui.theme.MusicSwitcherTheme
import com.sal7one.musicswitcher.repository.DataStoreProvider
import com.sal7one.musicswitcher.utils.MusicHelpers
import com.sal7one.musicswitcher.viewmodels.DeepLinkHandlerViewModel
import com.sal7one.musicswitcher.viewmodels.DeepLinkHandlerViewModelFactory


class DeepLinkHandlerActivity : ComponentActivity() {

    private lateinit var viewModel: DeepLinkHandlerViewModel
    private lateinit var data: Uri
    private lateinit var dataStoreProvider: DataStoreProvider
    private var action: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataStoreProvider = DataStoreProvider(applicationContext).getInstance()
        viewModel = ViewModelProvider(
            this,
            DeepLinkHandlerViewModelFactory(dataStoreProvider)
        )[DeepLinkHandlerViewModel::class.java]

        data = intent?.data!!
        intent?.action.also { action = it }

        setContent {
            MusicSwitcherTheme {
                val theScreenUiState = viewModel.optionScreenState.collectAsState()
                val loadingChoice = theScreenUiState.value.loading
                if (loadingChoice) {

                    val imageLoader = ImageLoader.Builder(LocalContext.current)
                        .components {
                            if (SDK_INT >= 28) {
                                add(ImageDecoderDecoder.Factory())
                            } else {
                                add(GifDecoder.Factory())
                            }
                        }
                        .build()

                    val painter = rememberAsyncImagePainter(
                        model = R.drawable.loading,
                        imageLoader = imageLoader
                    )
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painter,
                            contentDescription = getString(R.string.loading_icon)
                        )
                        Text(
                            getString(R.string.depend_on_intenet_tip),
                            style = MaterialTheme.typography.body1.copy(
                                Color.White
                            )
                        )
                    }
                }
            }

            // data comes from datastore
            viewModel.chosenProvider.observe(this) {
                viewModel.handleDeepLink(data) // start opening app
            }

            // Instantiate the RequestQueue.
            viewModel.sameApp.observe(this) {
                if (it) {
                    val appPackage = MusicHelpers.getMusicAppPackage(data.toString())
                    // Same provider - sameApp
                    openApp(action, data, appPackage)
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

                            // Different Provider - differentApp
                            val uri = Uri.parse(searchURL + query)
                            openApp(Intent.ACTION_VIEW, uri, viewModel.musicPackage.value!!)
                        },
                        {
                            Log.e("DEEP_LINK", "Volley Error")
                        }
                    )
                    queue.add(stringRequest)
                }
            }
        }
    }

    private fun openApp(iAction: String?, iUri: Uri, appPackage: String) {
        val i = Intent(iAction, iUri)
        i.setPackage(appPackage)
        try {
            i.apply {
                if (SDK_INT >= Build.VERSION_CODES.R) {
                    flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_REQUIRE_NON_BROWSER
                }
            }
            startActivity(i)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                this,
                getString(R.string.app_not_installed, MusicHelpers.packageNameToApp(appPackage)),
                Toast.LENGTH_LONG
            ).show()
            onStop()
        }
    }

    override fun onStop() {
        super.onStop()
        finishAndRemoveTask()
    }
}