package com.nocuntry.c1322ftkotlin.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.matias141201.NASAImage.components.ChatCard


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimationChat(
    isLoading: Boolean,
    translateComplete: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {

    AnimatedContent(
        targetState = isLoading,
        modifier = Modifier
            .fillMaxWidth(),
        content = {

            if (isLoading) {
                ChatCard(text = "...", background = Color.Black)

            } else {
                translateComplete()
            }
        },

        transitionSpec = {
            fadeIn(animationSpec = tween(2000))
            slideInHorizontally(
                initialOffsetX = {
                    -it
                }
            ) with slideOutHorizontally(
                targetOffsetX = {
                    it
                }
            )
        }
    )


}
