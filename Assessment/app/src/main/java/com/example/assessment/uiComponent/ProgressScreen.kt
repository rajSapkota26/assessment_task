package com.example.samplesetting.component

import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.example.assessment.R

@Composable
fun ProgressScreen(navHostController: NavHostController, message: String = "Loading....") {

    Dialog(properties = DialogProperties(
        usePlatformDefaultWidth = false,
        dismissOnBackPress = true,
        dismissOnClickOutside = false
    ),
        onDismissRequest = { }) {
        Surface(
            modifier = Modifier,
            shape = RoundedCornerShape(5.dp),
            color = Color.Transparent
        ) {
            Greeting()

        }
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun Greeting() {
    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .wrapContentSize()
    ) {

        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(150.dp)
                .background(Color.White)
        ) {
            CircularProgressIndicator(
                color = MaterialTheme .colorScheme.primary,
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
                strokeWidth = 15.dp
            )
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .align(Alignment.Center)
                    .size(130.dp)
                    .background(Color.White)
            ) {
                val imageLoader = ImageLoader.Builder(LocalContext.current)
                    .components {
                        if (Build.VERSION.SDK_INT >= 28) {
                            add(ImageDecoderDecoder.Factory())
                        } else {
                            add(GifDecoder.Factory())
                        }
                    }
                    .build()


                Image(
                    painter = rememberAsyncImagePainter(R.drawable.loader, imageLoader),
                    contentDescription = null,
                    modifier = Modifier
                        .size(90.dp)
                        .align(Alignment.Center)
                )


            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun ProgressScreenPreview() {
    ProgressScreen(navHostController = rememberNavController())
}
