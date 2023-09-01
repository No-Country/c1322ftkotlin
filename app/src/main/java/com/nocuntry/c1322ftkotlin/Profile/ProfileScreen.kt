package com.nocuntry.c1322ftkotlin.Profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.nocuntry.c1322ftkotlin.AppScreens

@Composable
fun ProfileScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFF0A0A0A)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )

    {
        // Mostrar la información del perfil del usuario, como el nombre de usuario y la biografía

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            // Navegar a la pantalla de edición de perfil al hacer clic en el botón
            navController.navigate(AppScreens.EditProfile.route)
        }) {
            Text("Editar Perfil")
        }
    }
}
