package com.nocuntry.c1322ftkotlin.Login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser

    val authState = MutableStateFlow<AuthState>(AuthState.None)

    fun register(email: String, password: String, confirmPassword: String) {
        if (password != confirmPassword) {
            authState.value = AuthState.Error("Passwords do not match")
            return
        }

        viewModelScope.launch {
            try {
                auth.createUserWithEmailAndPassword(email, password)
                authState.value = AuthState.Success
            } catch (e: Exception) {
                authState.value = AuthState.Error(e.localizedMessage ?: "Registration error")
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password)
                authState.value = AuthState.Success
            } catch (e: Exception) {
                authState.value = AuthState.Error(e.localizedMessage ?: "Login error")
            }
        }
    }
}