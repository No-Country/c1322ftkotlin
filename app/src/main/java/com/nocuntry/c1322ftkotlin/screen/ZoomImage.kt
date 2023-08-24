package com.nocuntry.c1322ftkotlin.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ZoomableImage(
    navController: NavController,
    image: String?,
    formattedDate: String?
) {
    val scale = remember { mutableStateOf(1f) }
    val offsetState = remember { mutableStateOf(Offset(0f, 0f)) }
    val rotationState = remember { mutableStateOf(0f) }
    var imageSize by remember { mutableStateOf(Size(0f, 0f)) }

    Box(
        modifier = Modifier
            .clip(RectangleShape)
            .fillMaxSize()
            .background(Color.Black)
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, rotation ->

                    val maxScale = maxOf(1f, scale.value * zoom)
                    val maxOffsetX = (imageSize.width * maxScale - size.width) / 2
                    val maxOffsetY = (imageSize.height * maxScale - size.height) / 2

                    scale.value *= zoom

                    rotationState.value += rotation

                    val newOffsetX = (offsetState.value.x + pan.x).coerceIn(-maxOffsetX, maxOffsetX)
                    val newOffsetY = (offsetState.value.y + pan.y).coerceIn(-maxOffsetY, maxOffsetY)

                    offsetState.value = Offset(newOffsetX, newOffsetY)
                }
            }
            .onGloballyPositioned { coordinates ->
                imageSize =
                    Size(coordinates.size.width.toFloat(), coordinates.size.height.toFloat())
            }
    ) {

        Image(
            modifier = Modifier
                .align(Alignment.Center)
                .graphicsLayer(
                    scaleX = maxOf(1f, minOf(10f, scale.value)),
                    scaleY = maxOf(1f, minOf(10f, scale.value)),
                    translationX = offsetState.value.x,
                    translationY = offsetState.value.y
                )
                .pointerInput(Unit) {
                    detectTapGestures {

                    }
                },
            contentDescription = null,
            painter = rememberImagePainter("https://apod.nasa.gov/apod/image/$formattedDate/$image")
        )
    }
}



