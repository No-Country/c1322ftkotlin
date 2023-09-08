package com.nocuntry.c1322ftkotlin.Profile

import UserProfile
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    navController: NavHostController,
    userProfile: UserProfile
) {
    var editedUserProfile by remember { mutableStateOf(userProfile.copy()) }
    var showMessage by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Profile") },
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
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                // Campos de edición
                TextField(
                    value = editedUserProfile.firstName,
                    onValueChange = { editedUserProfile = editedUserProfile.copy(firstName = it) },
                    label = { Text("First Name") }
                )
                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = editedUserProfile.lastName,
                    onValueChange = { editedUserProfile = editedUserProfile.copy(lastName = it) },
                    label = { Text("Last Name") }
                )
                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = editedUserProfile.email,
                    onValueChange = { editedUserProfile = editedUserProfile.copy(email = it) },
                    label = { Text("Email") }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Botón de guardar
                Button(
                    onClick = {
                        // Guardar los cambios
                        userProfile.copyFrom(editedUserProfile)
                        showMessage = true
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Guardar")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Mensaje de cambios guardados
                if (showMessage) {
                    Text("Cambios guardados")
                }
            }
        }
    )
}








