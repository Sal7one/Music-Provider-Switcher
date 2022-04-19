package com.sal7one.musicswitcher.compose.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sal7one.musicswitcher.compose.ui.theme.BtnUpdate_color


@Composable
fun UpdateButton(btnText: String, onClickMethod: () -> Unit) {
    val shadowColor = Color.White
    Box(
        modifier = Modifier
            .coloredShadow(
                shadowColor, alpha = 0.8f,
                shadowRadius = 11.dp,
            ),
        contentAlignment = Alignment.Center
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(
                backgroundColor = BtnUpdate_color,
            ),
            modifier = Modifier
                .clip(shape = RoundedCornerShape(7.dp))
                .width(178.dp),
            onClick = { onClickMethod() })
        {
            Text(text = btnText, style = MaterialTheme.typography.button)
        }
    }
}