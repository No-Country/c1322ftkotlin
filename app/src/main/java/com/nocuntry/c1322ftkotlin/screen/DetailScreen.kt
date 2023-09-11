package com.nocuntry.c1322ftkotlin.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.nocuntry.c1322ftkotlin.AppScreens
import com.nocuntry.c1322ftkotlin.IAViewModel
import com.nocuntry.c1322ftkotlin.R
import kotlinx.coroutines.DelicateCoroutinesApi


@OptIn(ExperimentalCoilApi::class, ExperimentalMaterial3Api::class, DelicateCoroutinesApi::class)
@Composable
fun DetailScreen(
    navController: NavController,
    image: String?,
    formattedDate: String?,
    title: String?,
    explanation: String?,
    date: String?,
    viewModel: IAViewModel
) {

    viewModel.Info = ""

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Image(
                    modifier = Modifier
                        .height(400.dp)
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(
                                route = AppScreens.ZoomImage.route +
                                        "/$image/$formattedDate"
                            )

                        },
                    contentDescription = title,
                    painter = rememberImagePainter("https://apod.nasa.gov/apod/image/$formattedDate/$image")
                )
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                title?.let {
                    Text(
                        it,
                        color = Color.White,
                        fontSize = 30.sp,
                        modifier = Modifier
                            .padding(top = 4.dp)
                    )
                }
            }

            Box(
                contentAlignment = Alignment.BottomEnd,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp)

            ) {

                IconButton(onClick = { viewModel.translate(explanation.toString()) }) {

                    Icon(
                        painter = painterResource(id = R.drawable.baseline_translate_24),
                        contentDescription = "translate icon",
                        tint = Color.White
                    )
                }

            }

            ShimermDetail(isLoading = viewModel.loading, translateComplete = {

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    if (viewModel.translateText.isEmpty()) {
                        explanation?.let {
                            Text(
                                it,
                                color = Color.White,
                                fontSize = 20.sp,
                                modifier = Modifier.padding(horizontal = 4.dp)
                            )
                        }

                    } else {
                        AnimatedVisibility(visible = viewModel.translateText.isNotEmpty()) {
                            Text(
                                text = viewModel.translateText, color = Color.White,
                                fontSize = 20.sp,
                                modifier = Modifier.padding(horizontal = 4.dp)
                            )
                        }
                    }
                }
            })

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                date?.let {
                    Text(
                        "Date: $it",
                        color = Color.White,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(top = 2.dp, bottom = 2.dp)
                            .padding(horizontal = 4.dp)
                    )
                }
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 2.dp)
            ) {

                val buttonColor = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.White
                )

                Button(
                    colors = buttonColor,
                    onClick = {
                        navController.navigate(
                            route = AppScreens.ChatScreen.route +
                                    "/$explanation"
                        )
                        viewModel.welcomeQuestion  = false
                    }
                ) {
                    Text(
                        text = "Aprende mas con HAL", color = Color.Black
                    )

                }
            }
        }
    }
}






