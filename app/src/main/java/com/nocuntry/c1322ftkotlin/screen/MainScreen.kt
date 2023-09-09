package com.nocuntry.c1322ftkotlin.screen

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.nocuntry.c1322ftkotlin.AppScreens
import com.nocuntry.c1322ftkotlin.IAViewModel
import com.nocuntry.c1322ftkotlin.R
import com.nocuntry.c1322ftkotlin.model.ApodList
import com.nocuntry.c1322ftkotlin.model.ApodResponse
import com.nocuntry.c1322ftkotlin.model.NasaApiService

fun transformDateFormat(inputDate: String): String {
    val year = inputDate.substring(2, 4)
    val month = inputDate.substring(5, 7)
    return "$year$month"
}

fun transformText(explanation: String): String {
    return explanation.replace("/", "\\")
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalCoilApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Main(apiService: NasaApiService, navController: NavController, viewModel: IAViewModel) {

    var apodListState by remember { mutableStateOf(emptyList<ApodResponse>()) }

    viewModel.translateText = ""

    viewModel.Info = ""

    var isLoading by remember {
        mutableStateOf(true)
    }

    var load by remember {
        mutableStateOf(false)
    }

    val explanation =
        "Entre 1969 y 1972, la NASA llevó a cabo las misiones Apollo, que permitieron que los astronautas humanos caminaran sobre la Luna. La misión Apollo 11, en julio de 1969, fue la primera en lograrlo con éxito. Neil Armstrong y Buzz Aldrin se convirtieron en los primeros humanos en caminar sobre la Luna"

    LaunchedEffect(apiService) {
        apodListState += ApodList(apiService).getApodList().value
    }

    Scaffold(

        containerColor = Color.Black,
        floatingActionButton = {

            if (load) {
                FloatingActionButton(
                    onClick = {
                        navController.navigate(
                            route = AppScreens.ChatScreen.route +
                                    "/$explanation"
                        )

                    },
                    shape = CircleShape,
                    contentColor = Color.White
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.chatgptlogo),
                        contentDescription = "HAL Button",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(60.dp)
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        modifier = Modifier.padding(bottom = 5.dp)
    ) {

        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {

            if (apodListState.isNotEmpty()) {
                isLoading = false
            }

            ShimmerListItem(
                isLoading = isLoading,
                contentAfterLoading = {

                    LazyColumn(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    ) {

                        items(apodListState.reversed()) { apod ->

                            load = true

                            isLoading = true

                            Card(
                                colors = CardDefaults.cardColors(containerColor = Color.Black),
                                shape = RoundedCornerShape(8.dp),
                                elevation = CardDefaults.cardElevation(
                                    defaultElevation = 10.dp
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                                    .clickable {
                                        val imageUrl = Uri.parse(apod.hdurl).lastPathSegment
                                        val formattedDate = transformDateFormat(apod.date)
                                        val text = transformText(apod.explanation)
                                        val title = transformText(apod.title)
                                        navController.navigate(
                                            route = AppScreens.Detail.route +
                                                    "/$imageUrl/$formattedDate/$title/$text/${apod.date}"
                                        )
                                    }
                                    .shadow(
                                        elevation = 4.dp,
                                        shape = RoundedCornerShape(20.dp),
                                        spotColor = Color.White
                                    )
                            ) {
                                Column(
                                    modifier = Modifier.padding(12.dp)
                                ) {
                                    Image(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(200.dp)
                                            .clip(RoundedCornerShape(10.dp)),
                                        contentScale = ContentScale.Crop,
                                        contentDescription = apod.title,
                                        painter = rememberImagePainter(data = apod.url),
                                    )
                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 8.dp)
                                    ) {
                                        Text(
                                            text = apod.title,
                                            fontSize = 18.sp,
                                            color = Color.White
                                        )
                                    }

                                }
                            }

                        }
                    }
                })
        }
    }
}

