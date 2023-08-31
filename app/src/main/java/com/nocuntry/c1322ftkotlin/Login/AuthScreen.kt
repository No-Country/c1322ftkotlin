package com.nocuntry.c1322ftkotlin.Login

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GoogleAuthProvider
import com.nocuntry.c1322ftkotlin.Login.AuthState
import com.nocuntry.c1322ftkotlin.AppScreens
import com.nocuntry.c1322ftkotlin.R

@Composable
fun AuthScreen(navController: NavHostController,
               onAuthStateChanged: (authState: AuthState) -> Unit) {

    val viewModel: AuthViewModel = viewModel()

    val currentAuthMode by viewModel.currentAuthMode.collectAsState()
    val authState by viewModel.authState.collectAsState() // Usar directamente authState en lugar de authStateFlow.collectAsState()

    LaunchedEffect(authState) { // No necesitas authStateFlow.value.collect aquí
        onAuthStateChanged(authState)
    }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val context = LocalContext.current
    val googleSignInClient = createGoogleSignInClient(context)
    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        handleGoogleSignInResult(task) { authState ->
        }
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFF03122C)), // Color de fondo pantalla login
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
        Text(
            text = "Welcome to the Astronomy App", //tambien se puede modificar el saludo de incio de la app
            fontSize = 18.sp,
            color = Color.White,  // Aplicar el color de texto de bienvenida al color que se quiera
            fontStyle = FontStyle.Italic
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email", color = Color.White) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
            textStyle = TextStyle(color = Color.White)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password", color = Color.White) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            textStyle = TextStyle(color = Color.White)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password", color = Color.White, fontSize = 18.sp) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            textStyle = TextStyle(color = Color.White)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                if (currentAuthMode == AuthMode.Login) {
                    viewModel.login(email, password)
                } else {
                    viewModel.register(email, password, confirmPassword)
                }
            }) {
                Text(if (currentAuthMode == AuthMode.Login) "Log In" else "Register")
            }
        }


        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val signInIntent = googleSignInClient.signInIntent
            googleSignInLauncher.launch(signInIntent)
        }) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.google_btn),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Log In with Google")
            }
        }

        Button(onClick = {
            navController.navigate(AppScreens.Profile.route)
        }) {
            Text("View Profile")
        }

        Button(onClick = {
            navController.navigate(AppScreens.EditProfile.route)
        }) {
            Text("Edit Profile")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (val currentState = authState) {
            is AuthState.Success -> {
                // authentication success
            }

            is AuthState.Error -> {
                Text(currentState.errorMessage, color = Color.Red)
            }

            else -> Unit
        }
    }
}

@Composable
fun createGoogleSignInClient(context: Context): GoogleSignInClient {
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken("777216942594-bio9e9s1diu3uno21m0jtglp142r6q8g.apps.googleusercontent.com") // Se hace el reemplaza con el Web Client ID de Firebase
        .requestEmail()
        .build()

    return GoogleSignIn.getClient(context, gso)
}
fun handleGoogleSignInResult(
    task: Task<GoogleSignInAccount>,
    onAuthStateChanged: (authState: AuthState) -> Unit
) {
    try {
        val account = task.getResult(ApiException::class.java)
        if (account != null) {
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener { authTask ->
                    if (authTask.isSuccessful) {
                        onAuthStateChanged(AuthState.Success)
                    } else {
                        onAuthStateChanged(AuthState.Error("Google sign-in failed"))
                    }
                }
        }
    } catch (e: ApiException) {
        onAuthStateChanged(AuthState.Error("Google sign-in error"))
    }
}



