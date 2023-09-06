package com.nocuntry.c1322ftkotlin

import ProfileScreen
import RegisterScreen
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nocuntry.c1322ftkotlin.Login.AuthScreen
import com.nocuntry.c1322ftkotlin.Login.AuthState
import com.nocuntry.c1322ftkotlin.Profile.EditProfileScreen
import com.nocuntry.c1322ftkotlin.model.NasaApiService
import com.nocuntry.c1322ftkotlin.screen.ChatScreen
import com.nocuntry.c1322ftkotlin.screen.DetailScreen
import com.nocuntry.c1322ftkotlin.screen.Main
import com.nocuntry.c1322ftkotlin.screen.ZoomableImage


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(apiService: NasaApiService) {
    val navController = rememberNavController()
    val iaviewModel = viewModel<IAViewModel>()

    NavHost(
        navController = navController,
        startDestination = AppScreens.Login.route
    ) {

        composable(route = AppScreens.Home.route) {
            Main(
                apiService,
                navController,
                iaviewModel
            )
        }

        composable(route = AppScreens.Login.route) {
            AuthScreen(navController) { authState: AuthState ->
                when (authState) {
                    AuthState.Authenticated -> {
                        navController.navigate(AppScreens.Home.route)
                    }

                    AuthState.Unauthenticated -> {
//                        Text("Usuario no autenticado", color = Color.Red)
                        // Se puede o no mostrar un mensaje  aquí
                    }

                    is AuthState.Error -> {
                        // Manejar el caso de autenticación con error
//                        Text("Error de autenticación: ${authState.errorMessage}", color = Color.Red)
                    }

                    else -> Unit
                }
            }
        }
        composable(route = AppScreens.Profile.route) {
            ProfileScreen(navController)
        }

        composable(route = AppScreens.Register.route) {
            RegisterScreen(navController, viewModel = viewModel())
        }


        composable(route = AppScreens.Detail.route + "/{image}/{formattedDate}/{title}/{explanation}/{date}",
            arguments = listOf(navArgument(name = "image") {
                type = NavType.StringType
            },
                navArgument(name = "formattedDate") {
                    type = NavType.StringType
                },
                navArgument(name = "title") {
                    type = NavType.StringType
                },
                navArgument(name = "explanation") {
                    type = NavType.StringType
                },
                navArgument(name = "date") {
                    type = NavType.StringType
                }
            )
        )
        {
            DetailScreen(
                navController,
                it.arguments?.getString("image"),
                it.arguments?.getString("formattedDate"),
                it.arguments?.getString("title"),
                it.arguments?.getString("explanation"),
                it.arguments?.getString("date"),
                iaviewModel
            )

        }
        composable(route = AppScreens.ZoomImage.route + "/{image}/{formattedDate}",
            arguments = listOf(navArgument(name = "image") {
                type = NavType.StringType
            },
                navArgument(name = "formattedDate") {
                    type = NavType.StringType
                }
            )) {
            ZoomableImage(
                navController,
                it.arguments?.getString("image"),
                it.arguments?.getString("formattedDate")
            )
        }
        composable(route = AppScreens.ChatScreen.route + "/{explanation}") {
            ChatScreen(
                navController,
                it.arguments?.getString("explanation"),
                iaviewModel
            )
        }
    }
}

