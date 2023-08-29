package com.nocuntry.c1322ftkotlin.Login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser

    private val _currentAuthMode = MutableStateFlow(AuthMode.Login)
    val currentAuthMode: StateFlow<AuthMode> = _currentAuthMode


    val authState = MutableStateFlow<AuthState>(AuthState.None)
    var authMode: AuthMode = AuthMode.Login // Agregamos una variable para rastrear el modo de autenticación

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

    fun toggleAuthMode() {
        _currentAuthMode.value = if (_currentAuthMode.value == AuthMode.Login) {
            AuthMode.Register
        } else {
            AuthMode.Login
        }
    }
}