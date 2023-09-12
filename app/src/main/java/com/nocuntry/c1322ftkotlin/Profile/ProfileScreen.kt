import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.nocuntry.c1322ftkotlin.AppScreens
import com.nocuntry.c1322ftkotlin.Profile.ProfileViewModel
import com.nocuntry.c1322ftkotlin.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ProfileScreen(
    navController: NavHostController
) {
    val viewModel: ProfileViewModel = viewModel()

    // Datos del usuario
    val user = UserProfile(
        firstName = "Sebastian",
        lastName = "Andrade",
        email = "jsebas_andrade@example.com",
        summary = "Front-end Developer",
        followers = "100",
        following = "50",
        profileImageRes = R.drawable.baseline_account_circle_24,
        headerImageRes = R.drawable.night_profile// Reemplaza con el recurso de tu imagen de cabecera
    )

    viewModel.loadProfileData(user)
    val userInfo by viewModel.userInfo.collectAsState()

    var posts by remember { mutableStateOf(mutableStateListOf<Post>()) }


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
                ProfileHeader(user)

                CreatePostButton(navController, posts)

                CreatePostScreen(navController, posts)


            }
        }
    )
}

@Composable
fun ProfileHeader(userProfile: UserProfile) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black) // Cambia el color de fondo según tus preferencias
            .padding(16.dp)
    ) {
        // Imagen de cabecera
        Image(
            painter = painterResource(id = userProfile.headerImageRes),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp) // Ajusta la altura según tus necesidades
                .clip(MaterialTheme.shapes.medium)
        )

        // Datos del usuario
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            // Imagen de perfil
            Image(
                painter = painterResource(id = userProfile.profileImageRes),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clip(MaterialTheme.shapes.small)
                    .background(Color.Black)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = "${userProfile.firstName} ${userProfile.lastName}",
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = userProfile.email,
                    fontSize = 16.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = userProfile.summary,
                    fontSize = 16.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = userProfile.followers,
                    fontSize = 16.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = userProfile.following,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun CreatePostButton(
    navController: NavController,
    posts: MutableList<Post>
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(
            onClick = {
                // Navega a la pantalla de creación de publicaciones
                navController.navigate("CreatePost") // Asegúrate de que la ruta sea correcta
            },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomEnd) // Alinea el botón en la esquina inferior derecha
        ) {
            Text("Crear Publicación")
        }
    }
}

@Composable
fun CreatePostScreen(
    navController: NavController,
    posts: MutableList<Post>
) {
    var postContent by remember { mutableStateOf("") }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Campo de texto para ingresar la publicación
        BasicTextField(
            value = postContent,
            onValueChange = { postContent = it },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    // Crear y agregar una nueva publicación
                    if (postContent.isNotBlank()) {
                        val currentTimeMillis = System.currentTimeMillis()
                        val newPost = Post(content = postContent, timestamp = currentTimeMillis)
                        posts.add(newPost)
                        // Limpia el campo de texto después de publicar
                        postContent = ""
                        // Navega de nuevo a la pantalla de perfil
                        navController.navigateUp()
                    }
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón "Enviar" para publicar
        Button(
            onClick = {
                // Crear y agregar una nueva publicación
                if (postContent.isNotBlank()) {
                    val currentTimeMillis = System.currentTimeMillis()
                    val newPost = Post(content = postContent, timestamp = currentTimeMillis)
                    posts.add(newPost) // Agrega la nueva publicación a la lista
                    // Limpia el campo de texto después de publicar
                    postContent = ""
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Icon(imageVector = Icons.Default.Send, contentDescription = "Enviar")
        }

    }
    Spacer(modifier = Modifier.height(16.dp))

    // Lista de post
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(posts) { post ->
            PostItem(post = post)
        }
    }
}


@Composable
fun PostItem(post: Post) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Maneja la acción cuando se hace clic en una publicación */ }
            .padding(8.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = post.content, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Publicado el ${formatTimestamp(post.timestamp)}", fontSize = 12.sp)
        }
    }
}

data class Post(
    val content: String,
    val timestamp: Long
)

fun formatTimestamp(timestamp: Long): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
    val date = Date(timestamp)
    return dateFormat.format(date)
}
