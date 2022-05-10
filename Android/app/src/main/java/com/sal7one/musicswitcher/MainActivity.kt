package com.sal7one.musicswitcher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.core.content.ContextCompat
import com.sal7one.musicswitcher.ui.ApplicationScreen
import com.sal7one.musicswitcher.ui.ui.theme.MusicSwitcherTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            this.window.statusBarColor = ContextCompat.getColor(this, R.color.appbarColor)
            MusicSwitcherTheme {
                // A surface container using the 'background' color from the theme
                Scaffold {
                    ApplicationScreen()
                }
            }
        }
    }
}

