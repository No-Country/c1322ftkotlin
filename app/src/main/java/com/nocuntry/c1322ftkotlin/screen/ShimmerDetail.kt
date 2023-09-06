package com.nocuntry.c1322ftkotlin.screen

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
import androidx.compose.ui.unit.dp


@Composable
fun ShimermDetail(
    isLoading: Boolean,
    translateComplete: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {


    if (isLoading) {

        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            repeat(20) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(20.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .padding(2.dp)
                        .shimmerEffect()
                ) {
                    Text(
                        text = "",
                        modifier = Modifier
                            .padding(4.dp)
                            .clip(RoundedCornerShape(10.dp))
                    )
                }
            }
        }
    } else {
        translateComplete()
    }

}