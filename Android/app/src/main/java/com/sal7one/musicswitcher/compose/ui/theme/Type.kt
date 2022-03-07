package com.sal7one.musicswitcher.compose.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.sal7one.musicswitcher.R

// Set of Material typography styles to start with
val appFont = FontFamily(
    Font(R.font.play_fair_regular, FontWeight.Normal)
)
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = appFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    h2 = TextStyle(
        fontFamily = appFont,
        fontWeight = FontWeight.Normal,
        fontSize = 19.sp
    ),
    button = TextStyle(
        fontFamily = appFont,
        fontWeight = FontWeight.W500,
        fontSize = 15.sp
    )
)