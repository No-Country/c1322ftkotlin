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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.nocuntry.c1322ftkotlin.AppScreens
import com.nocuntry.c1322ftkotlin.Profile.ProfileViewModel
import com.nocuntry.c1322ftkotlin.R

@Composable
fun ProfileScreen(
    navController: NavHostController) {

    val viewModel: ProfileViewModel = viewModel()

    // Datos del usuario
    val user = UserProfile(
        firstName = "Sebastian",
        lastName = "Andrade",
        email = "jsebas_andrade@example.com",
        summary = "Front-end Developer",
        followers = "100",
        following = "50",
        profileImageRes = R.drawable.baseline_account_circle_24
    )

    viewModel.loadProfileData(user)
    val userInfo by viewModel.userInfo.collectAsState()



    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                backgroundColor = Color.Black, // Fondo negro
                contentColor = Color.White, // Texto e iconos en blanco
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
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                ProfileHeader(user)

                Spacer(modifier = Modifier.height(16.dp))

                ProfileContent(user) // Pasa el objeto UserProfile

                // Botón para editar perfil
                Button(
                    onClick = {
                        // Navegar a la pantalla de edición de perfil
                        navController.navigate(AppScreens.EditProfile.route)
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp)
                ) {
                    Text("Editar Perfil")
                }
            }
        }
    )
}

@Composable
fun ProfileHeader(userProfile: UserProfile) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.DarkGray) // Cambia el color de fondo según tus preferencias
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
                painter = painterResource(id = userProfile.profileImageRes), // Reemplaza con la imagen de perfil adecuada
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clip(MaterialTheme.shapes.small)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = "${userProfile.firstName} ${userProfile.lastName}",
                    fontSize = 18.sp,
                    color = Color.White
                )
                Text(
                    text = userProfile.email,
                    fontSize = 16.sp,
                    color = Color.White
                )
                Text(
                    text = userProfile.summary,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }
    }
}
@Composable
fun ProfileContent(userProfile: UserProfile) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Summary:",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(8.dp)
        )
        Text(
            text = userProfile.summary,
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier.padding(8.dp)
        )
        Text(
            text = "Followers: ${userProfile.followers}",
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier.padding(8.dp)
        )
        Text(
            text = "Following: ${userProfile.following}",
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier.padding(8.dp)
        )
    }
}