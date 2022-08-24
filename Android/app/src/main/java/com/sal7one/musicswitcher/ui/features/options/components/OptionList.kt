package com.sal7one.musicswitcher.ui.features.options.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sal7one.musicswitcher.ui.common.Switch


@Composable
fun OptionList(
    text: String, state: Boolean, onCheckChanged: (Boolean) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
        ) {
            Box(modifier = Modifier.padding(start = 51.dp)) {
                Text(
                    text = text,
                    style = TextStyle(fontSize = 21.sp)
                )
            }
            Box(modifier = Modifier.padding(end = 65.dp)) {
                Switch(state) {
                    onCheckChanged(it)
                }
            }
        }
    }
}