package com.nocuntry.c1322ftkotlin.Profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.nocuntry.c1322ftkotlin.AppScreens
import com.nocuntry.c1322ftkotlin.R

@Composable
fun EditProfileScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel,

) {
    val userInfo by viewModel.userInfo.collectAsState()

    var newName by remember { mutableStateOf(userInfo.name) }
    var newEmail by remember { mutableStateOf(userInfo.email) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFF0A0A0A)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.baseline_account_circle_24),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )


        Text(
            text = "Edit Profile",
            fontSize = 18.sp,
            color = Color.White,
            fontStyle = FontStyle.Normal
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = newName,
            onValueChange = { newName = it },
            label = { Text("Name", color = Color.White) },
            textStyle = TextStyle(color = Color.White)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = newEmail,
            onValueChange = { newEmail = it },
            label = { Text("Email", color = Color.White) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
            textStyle = TextStyle(color = Color.White)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.updateUserInfo(UserInfo(name = newName, email = newEmail))
                navController.popBackStack()
            }
        ) {
            Text("Save Changes")
        }
    }
}
