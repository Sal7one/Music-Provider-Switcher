package com.sal7one.musicswitcher.ui.features.deepLinkHandler

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.sal7one.musicswitcher.R

@Composable
fun LoadingUi() {
    val context = LocalContext.current

    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }.build()

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
            contentDescription = context.getString(R.string.loading_icon)
        )
        Text(
            context.getString(R.string.depend_on_intenet_tip),
            style = MaterialTheme.typography.body1.copy(
                Color.White
            )
        )
    }
}