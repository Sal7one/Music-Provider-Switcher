package com.sal7one.musicswitcher.compose.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sal7one.musicswitcher.R
import com.sal7one.musicswitcher.compose.ui.theme.AppPrimary_color
import com.sal7one.musicswitcher.compose.ui.theme.TextSecondary_color
import com.sal7one.musicswitcher.compose.ui.theme.primary_gradient_color

@Composable
fun AboutScreen() {
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        AppPrimary_color,
                        AppPrimary_color,
                        AppPrimary_color,
                        primary_gradient_color
                    )
                )
            )
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = stringResource(R.string.about_dev_heading),
            style = TextStyle(color = Color.White, fontSize = 24.sp),
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            Image(
                painter = painterResource(R.drawable.mypic),
                contentDescription = stringResource(R.string.cd_dev_image),
                contentScale = ContentScale.Fit,  // crop the image if it's not a square
                modifier = Modifier
                    .size(85.dp)
                    .clip(CircleShape) // clip to the circle shape
                    .border(
                        2.dp,
                        TextSecondary_color,
                        CircleShape
                    )
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = {
                                context.startActivity(
                                    Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse(context.getString((R.string.github_link)))
                                    )
                                )
                            },
                        )
                    }
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = stringResource(R.string.dev_name),
                modifier = Modifier
                    .padding(30.dp)
                    .scale(1.4f),
                style = MaterialTheme.typography.h2
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(R.string.about_developer),
            modifier = Modifier.padding(start = 10.dp),
            style = MaterialTheme.typography.h2
        )
        Spacer(modifier = Modifier.height(55.dp))
        Text(
            text = stringResource(R.string.about_app_heading),
            style = TextStyle(color = Color.White, fontSize = 24.sp),
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(R.string.about_this_app),
            modifier = Modifier.padding(start = 10.dp),
            style = MaterialTheme.typography.h2
        )
    }
}