package com.sal7one.musicswitcher.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.ui.graphics.toArgb
import androidx.core.content.ContextCompat
import com.sal7one.musicswitcher.R
import com.sal7one.musicswitcher.compose.ui.theme.AppPrimary_color
import com.sal7one.musicswitcher.compose.ui.theme.MusicSwitcherTheme

class MainUi : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            this.window.statusBarColor = ContextCompat.getColor(this, R.color.appbarColor)
            MusicSwitcherTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(
                ) {
                    ApplicationScreen()
                }
            }
        }
    }
}

