package com.sal7one.musicswitcher.ui.features.deepLinkHandler

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.Intent.FLAG_ACTIVITY_REQUIRE_NON_BROWSER
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.lifecycleScope
import com.sal7one.musicswitcher.R
import com.sal7one.musicswitcher.ui.ui.theme.MusicSwitcherTheme
import com.sal7one.musicswitcher.utils.MusicHelpers
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class DeepLinkHandlerActivity : ComponentActivity() {

    private lateinit var songData: Uri
    private val viewModel: DeepLinkHandlerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MusicSwitcherTheme {
                val deepLinkUiState = viewModel.deepLinkScreenState.collectAsState()
                val loadingChoice = deepLinkUiState.value.loading
                if (loadingChoice) {
                    LoadingUi()
                }
            }
        }

        // Intent Data
        songData = intent?.data!!

        // Data from datastore
        lifecycleScope.launchWhenStarted {
            viewModel.deepLinkScreenState.collectLatest {
                viewModel.handleDeepLink(songData)
            }
            viewModel.songURI.collectLatest { uri ->
                openApp(Uri.parse(uri))
            }
        }
    }

    private fun openApp(iUri: Uri) {
        val appIntent = Intent(Intent.ACTION_VIEW, iUri)
        val appPackage = viewModel.musicPackage
        try {
            appIntent.apply {
                setPackage(appPackage)
                if (SDK_INT >= Build.VERSION_CODES.R) {
                    flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_REQUIRE_NON_BROWSER
                }
            }
            startActivity(appIntent)
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