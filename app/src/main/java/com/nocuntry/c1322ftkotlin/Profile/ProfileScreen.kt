import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.nocuntry.c1322ftkotlin.AppScreens
import com.nocuntry.c1322ftkotlin.Profile.UserProfile
import com.nocuntry.c1322ftkotlin.R

@Composable
fun ProfileScreen(navController: NavHostController) {

    val profileImageRes = R.drawable.baseline_account_circle_24

    val user = UserProfile(
        firstName = "John",
        lastName = "Doe",
        email = "john.doe@example.com",
        summary = "Front-end Developer",
        followers = "100",
        following = "50",
        profileImageRes = R.drawable.baseline_account_circle_24

    )

    var isMenuExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        drawerContent = {
            SideMenu(
                userProfile = user,
                profileImageRes = profileImageRes, // Pasa profileImageRes aquí
                onLogout = {
                    // Realizar acciones de logout aquí
                    navController.navigate(AppScreens.Login.route)
                }
            )

        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                // Contenido de la pantalla de perfil
                Spacer(modifier = Modifier.height(16.dp))
                ProfileHeader(user)
                Spacer(modifier = Modifier.height(16.dp))
                ProfileContent()
            }
        }
    )
}


@Composable
fun ProfileHeader(userProfile: UserProfile) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Blue) // Cambia el color de fondo según tus preferencias
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            // Agrega la imagen del perfil aquí, asumiendo que userProfile tiene un campo para la imagen
            Image(
                painter = painterResource(id = R.drawable.baseline_account_circle_24), // Reemplaza con la imagen de perfil adecuada
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clip(MaterialTheme.shapes.small)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = "${userProfile.firstName} ${userProfile.lastName}",
                    fontSize = 20.sp,
                    color = Color.White
                )
                Text(
                    text = userProfile.email,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun ProfileContent() {
    // Aquí puedes agregar el contenido adicional de tu perfil
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(10) {
            // Agregar elementos de contenido aquí
            Text(text = "Item $it", fontSize = 20.sp)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun SideMenu(
userProfile: UserProfile,
profileImageRes: Int, // Agrega profileImageRes como parámetro
onLogout: () -> Unit
) {
    // Contenido del menú lateral
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Información del perfil
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { /* Navega a la página de perfil */ }
        ) {
            Image(
                painter = painterResource(id = profileImageRes), // Usa profileImageRes aquí
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clip(MaterialTheme.shapes.small)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${userProfile.firstName} ${userProfile.lastName}",
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Opciones de menú
        Text(
            text = "Menu Item 1",
            fontSize = 16.sp,
            modifier = Modifier.clickable { /* Realiza la acción correspondiente */ }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Menu Item 2",
            fontSize = 16.sp,
            modifier = Modifier.clickable { /* Realiza la acción correspondiente */ }
        )

        Spacer(modifier = Modifier.weight(1f))

        // Botón de Logout
        Text(
            text = "Logout",
            fontSize = 16.sp,
            color = Color.Red,
            modifier = Modifier.clickable {
                onLogout()
            }
        )
    }
}