package com.sal7one.musicswitcher.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.sal7one.musicswitcher.R
import com.sal7one.musicswitcher.ui.ui.theme.dialog_background_color

@Composable
fun CustomDialog(openDialogCustom: MutableState<Boolean>) {
    Dialog(onDismissRequest = { openDialogCustom.value = false }) {
        CustomDialogUI(showExplainDialog = openDialogCustom)
    }
}

//Layout
@Composable
fun CustomDialogUI(
    showExplainDialog: MutableState<Boolean>
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
        elevation = 8.dp
    ) {
        Column(
            Modifier
                .background(Color.White)
        ) {
            Text(
                text = stringResource(R.string.explain_steps),
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(16.dp)
            )
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .background(dialog_background_color),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                TextButton(onClick = {
                    showExplainDialog.value = false
                }) {
                    Text(
                        "Done",
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                }
                TextButton(onClick = {
                   // intent
                }) {
                    Text(
                        "Open Settings",
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                }
            }
        }
    }
}