package com.nocuntry.c1322ftkotlin.Login

import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GoogleAuthProvider
import com.nocuntry.c1322ftkotlin.Login.AuthState
import com.nocuntry.c1322ftkotlin.AppScreens
import com.nocuntry.c1322ftkotlin.R
import com.nocuntry.c1322ftkotlin.ui.theme.MyButtonColor

@Composable
fun AuthScreen(
    navController: NavHostController,
    onAuthStateChanged: (authState: AuthState) -> Unit
) {

    val viewModel: AuthViewModel = viewModel()

    val currentAuthMode by viewModel.currentAuthMode
    val authState by viewModel.authState

    val token = "777216942594-bio9e9s1diu3uno21m0jtglp142r6q8g.apps.googleusercontent.com"
    val context = LocalContext.current

    var showWelcomeMessage by remember { mutableStateOf(false) }


    LaunchedEffect(authState) { // No necesitas authStateFlow.value.collect aquí
        onAuthStateChanged(authState)
    }
    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            viewModel.signInWithGoogle(credential) {
                navController.navigate(AppScreens.Home.route)
            }
        } catch (ex: Exception) {
            Log.d("Orion App", "GoogleSignIn Falló")
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFF0A0A0A)), // Color de fondo pantalla login
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "Welcome to Orion", //tambien se puede modificar el saludo de incio de la app
            fontSize = 22.sp,
            color = Color.White,  // Aplicar el color de texto de bienvenida al color que se quiera
            fontStyle = FontStyle.Italic
        )

        Spacer(modifier = Modifier.height(18.dp))

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.height(25.dp))

        // Botón para crear una cuenta
        Button(
            onClick = {
                navController.navigate(AppScreens.Register.route)
            },
            modifier = Modifier
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .padding(8.dp),
            colors = ButtonDefaults.buttonColors(MyButtonColor),
        ) {
            Text(
                "Create An Account",
                style = TextStyle(
                    fontWeight = FontWeight.Bold, // Texto en negrita
                    fontSize = 18.sp, // Tamaño del texto
                    color = Color.White // Color de texto blanco
                )
            )
        }

        Spacer(modifier = Modifier.height(25.dp))


        //Boton inicio de sesion con Google
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .clickable {
                    val opciones = GoogleSignInOptions
                        .Builder(
                            GoogleSignInOptions.DEFAULT_SIGN_IN
                        )
                        .requestIdToken(token)
                        .requestEmail()
                        .build()

                    val googleSignInCliente = GoogleSignIn.getClient(context, opciones)
                    googleSignInLauncher.launch(googleSignInCliente.signInIntent)
                },
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = R.drawable.google_btn),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .padding(end = 10.dp), // Espacio entre la imagen y el texto
            )
            Text(
                "Login with Google",
                style = TextStyle(
                    fontWeight = FontWeight.Bold, // Texto en negrita
                    fontSize = 18.sp, // Tamaño del texto
                    color = Color.White // Color de texto blanco
                )
            )
        }
    }

    if (showWelcomeMessage) {
        AlertDialog(
            onDismissRequest = {
                showWelcomeMessage = false
            },
            title = {
                Text(text = "¡Bienvenido!")
            },
            text = {
                Text(text = "Has iniciado sesión con éxito.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        showWelcomeMessage = false
                    }
                ) {
                    Text(text = "Aceptar")
                }
            }
        )
    }
}







