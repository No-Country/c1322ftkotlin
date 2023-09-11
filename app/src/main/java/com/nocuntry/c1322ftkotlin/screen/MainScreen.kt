package com.nocuntry.c1322ftkotlin.screen

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalCoilApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Main(
    apiService: NasaApiService,
    navController: NavController,
    viewModel: IAViewModel,
) {
    var apodListState by remember { mutableStateOf(emptyList<ApodResponse>()) }
    var isLoading by remember { mutableStateOf(true) }

    // Estado para abrir o cerrar el menú lateral
    var isDrawerOpen by remember { mutableStateOf(false) }

    // Definir el estilo de texto directamente en la función
    val customTextStyle = TextStyle(
        fontSize = 20.sp,
        color = Color.White, // Color de texto personalizado
        fontWeight = FontWeight.Bold
    )

    LaunchedEffect(apiService) {
        apodListState += ApodList(apiService).getApodList().value
    }


    // Utiliza Scaffold para agregar el menú lateral
    Scaffold(
        topBar = {
            // Barra de la aplicación con el ícono del menú
            TopAppBar(
                title = {
                    Text(text = "SkyWonders")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            // Abre o cierra el menú lateral al hacer clic en el ícono
                            isDrawerOpen = !isDrawerOpen
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Abrir menú"
                        )
                    }
                }
            )
        },
        // Cuerpo del Scaffold
        content = {
            // Contenido principal de la pantalla
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .background(Color.Black)
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
                                isLoading = true
                                Card(
                                    colors = CardDefaults.cardColors(containerColor = Color.Black),
                                    shape = RoundedCornerShape(8.dp),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
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
                    }
                )
            }
        },
        // Menú lateral
        drawerContent = {
            // Contenido del menú lateral
            LazyColumn {
                // Cabecera del menú con una imagen
                item {
                    Image(
                        painter = painterResource(id = R.drawable.header),
                        contentDescription = "Cabecera del menú",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp) // Ajusta la altura según tus necesidades
                    )
                }

                // Opción "Profile"
                item {
                    Text(
                        text = "Profile",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        style = TextStyle(
                            fontSize = 18.sp,
                            color = Color.White // Color de texto en blanco
                        ),
                        onClick = {
                            // Navegar a la pantalla de perfil o ejecutar la acción correspondiente
                            navController.navigate(AppScreens.Profile.route)
                        }
                    )
                }

                // Opción "Notifications"
                item {
                    Text(
                        text = "Notifications",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        style = TextStyle(
                            fontSize = 18.sp,
                            color = Color.White // Color de texto en blanco
                        ),
                        onClick = {
                            // Navegar a la pantalla de notificaciones o ejecutar la acción correspondiente
                            navController.navigate(AppScreens.Notifications.route)
                        }
                    )
                }

                // Opción "Logout"
                item {
                    Text(
                        text = "Logout",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        style = TextStyle(
                            fontSize = 18.sp,
                            color = Color.White // Color de texto en blanco
                        ),
                        onClick = {
                            // Realizar la acción de cierre de sesión aquí
                            navController.navigate(AppScreens.Logout.route)
                        }
                    )
                }
            }
        },

// Abre o cierra el menú lateral según el estado
        drawerGesturesEnabled = isDrawerOpen,
// Indica si el menú lateral está abierto o cerrado
        drawerBackgroundColor = Color.Black // Cambia el color de fondo según tus preferencias
    )
}





