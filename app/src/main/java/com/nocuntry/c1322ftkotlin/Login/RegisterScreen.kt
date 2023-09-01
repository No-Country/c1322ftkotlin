package com.nocuntry.c1322ftkotlin.Login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.sp
import com.nocuntry.c1322ftkotlin.AppScreens
import com.nocuntry.c1322ftkotlin.R

@Composable
fun RegisterScreen(navController: NavHostController, viewModel: AuthViewModel) {

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFF0A0A0A)), // Color de fondo pantalla login
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp)) // Espacio margen de arriba del logo

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
        androidx.compose.material.Text(
            text = "Create Account", //tambien se puede modificar el saludo de incio de la app
            fontSize = 20.sp,
            color = Color.White,  // Aplicar el color de texto de bienvenida al color que se quiera
            fontStyle = FontStyle.Italic
        )

        Spacer(modifier = Modifier.height(16.dp))


        OutlinedTextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text("First Name", color = Color.White, fontSize = 16.sp) },
            textStyle = TextStyle(color = Color.White, fontSize = 16.sp)
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Last Name", color = Color.White, fontSize = 16.sp) },
            textStyle = TextStyle(color = Color.White, fontSize = 16.sp)
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email", color = Color.White, fontSize = 16.sp) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
            textStyle = TextStyle(color = Color.White, fontSize = 16.sp)
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password", color = Color.White, fontSize = 16.sp) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            textStyle = TextStyle(color = Color.White, fontSize = 16.sp)
        )
        Spacer(modifier = Modifier.height(8.dp))

        /*OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password", color = Color.White, fontSize = 16.sp) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            textStyle = TextStyle(color = Color.White, fontSize = 16.sp)
        )
        Spacer(modifier = Modifier.height(8.dp))*/

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            Button(
                onClick = {
                    viewModel.register(firstName, lastName, email, password, confirmPassword)
                    navController.navigate(AppScreens.Home.route)
                },
                modifier = Modifier
                    .padding(16.dp)
                    .wrapContentWidth(),
                colors = ButtonDefaults.buttonColors(),
                content = {
                    Text(
                        text = "Done",
                        style = MaterialTheme.typography.button.copy(color = MaterialTheme.colors.onPrimary)
                    )
                }
            )
        }
    }
}
            /*Button(onClick = {
                navController.navigate(AppScreens.Profile.route)
            }) {
                androidx.compose.material.Text("View Profile")
            }

            Button(onClick = {
                navController.navigate(AppScreens.EditProfile.route)
            }) {
                androidx.compose.material.Text("Edit Profile")
            }*/

