import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.material3.Button
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.nocuntry.c1322ftkotlin.AppScreens
import com.nocuntry.c1322ftkotlin.Login.AuthViewModel
import com.nocuntry.c1322ftkotlin.R
import com.nocuntry.c1322ftkotlin.ui.theme.MyButtonColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavHostController, viewModel: AuthViewModel) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var isPasswordVisible by remember { mutableStateOf(false) }
    var showPassword by remember { mutableStateOf(false) }

    var showSuccessDialog by remember { mutableStateOf(false) }

    var showErrorDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFF0A0A0A)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
        Text(
            text = "Create Account",
            fontSize = 25.sp,
            color = Color.White,
            fontStyle = FontStyle.Italic
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = firstName,
                onValueChange = { firstName = it },
                textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                label = { Text("First Name", color = Color.DarkGray, fontSize = 16.sp) }
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = lastName,
                onValueChange = { lastName = it },
                textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                label = { Text("Last Name", color = Color.DarkGray, fontSize = 16.sp) }
            )
            Spacer(modifier = Modifier.height(8.dp))

          TextField(
                value = email,
                onValueChange = { email = it },
                textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                label = { Text("Email", color = Color.DarkGray, fontSize = 16.sp) }
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = password,
                onValueChange = { password = it },
                textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                label = { Text("Password", color = Color.DarkGray, fontSize = 16.sp) },
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                trailingIcon = {
                    val eyeIcon = if (showPassword) R.drawable.ic_eye_closed else R.drawable.ic_eye
                    Image(
                        painter = painterResource(id = eyeIcon),
                        contentDescription = if (showPassword) "Hide Password" else "Show Password",
                        modifier = Modifier
                            .clickable {
                            showPassword = !showPassword
                        }
                    )
                }
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                label = { Text("Password", color = Color.DarkGray, fontSize = 16.sp) },
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                trailingIcon = {
                    val eyeIcon = if (showPassword) R.drawable.ic_eye_closed else R.drawable.ic_eye
                    Image(
                        painter = painterResource(id = eyeIcon),
                        contentDescription = if (showPassword) "Hide Password" else "Show Password",
                        modifier = Modifier
                            .clickable {
                                showPassword = !showPassword
                            }
                    )
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (firstName.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty() &&
                        password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                        if (password == confirmPassword) {
                            viewModel.register(firstName, lastName, email, password, confirmPassword)
                            showSuccessDialog = true
                            navController.navigate(AppScreens.Home.route) // Redirige al usuario a la pantalla de inicio de la aplicacion
                        } else {
                            showErrorDialog = true
                        }
                    } else {
                        showErrorDialog = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(MyButtonColor),
                content = {
                    Text(
                        text = "Register",
                        style = TextStyle(
                            fontSize = 18.sp, // Tamaño del texto
                            color = Color.White // Color de texto blanco
                        )
                    )
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    navController.navigate(AppScreens.Login.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(MyButtonColor),
                content = {
                    Text(
                        text = "Cancel",
                        style = TextStyle(
                            fontSize = 18.sp, // Tamaño del texto
                            color = Color.White // Color de texto blanco
                        )
                    )
                }
            )
        }
    }

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = {
                showSuccessDialog = false
            },
            title = {
                Text(text = "Registro Exitoso")
            },
            text = {
                Text(text = "Tu registro ha sido exitoso.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        showSuccessDialog = false
                    }
                ) {
                    Text(text = "Aceptar")
                }
            }
        )
    }

    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = {
                showErrorDialog = false
            },
            title = {
                Text(text = "Error en el registro")
            },
            text = {
                Text(text = "Por favor, completa todos los campos del formulario de registro y asegúrate de que las contraseñas coincidan.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        showErrorDialog = false
                    }
                ) {
                    Text(text = "Aceptar")
                }
            }
        )
    }
}
