package com.nocuntry.c1322ftkotlin.Login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.nocuntry.c1322ftkotlin.AppScreens
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.nocuntry.c1322ftkotlin.Login.AuthState
import com.nocuntry.c1322ftkotlin.Profile.ProfileEvent
import com.nocuntry.c1322ftkotlin.Profile.UserProfile

class AuthViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser

    private val _navigateToProfile = MutableStateFlow(false)
    val navigateToProfile: StateFlow<Boolean> = _navigateToProfile

    private val _navigateToEditProfile = MutableStateFlow(false)
    val navigateToEditProfile: StateFlow<Boolean> = _navigateToEditProfile

    private val _currentAuthMode = MutableStateFlow(AuthMode.Login)
    val currentAuthMode: StateFlow<AuthMode> = _currentAuthMode

    val authState = MutableStateFlow<AuthState>(AuthState.None)
    var authMode: AuthMode = AuthMode.Login // Agregamos una variable para rastrear el modo de autenticación

    val currentScreen: MutableState<AppScreens> = mutableStateOf(AppScreens.Login)
    val profileEvent: MutableState<ProfileEvent?> = mutableStateOf(null)

    private val _userProfile = MutableStateFlow<UserProfile?>(null)
    val userProfile: StateFlow<UserProfile?> = _userProfile

    fun register(firstName: String, lastName: String, email: String, password: String, confirmPassword: String) {
        if (password != confirmPassword) {
            authState.value = AuthState.Error("Passwords do not match")
            return
        }

        viewModelScope.launch {
            try {
                auth.createUserWithEmailAndPassword(email, password)
                // Crear aqui el UserProfile y guárdarlo en la propiedad _userProfile
                _userProfile.value = UserProfile(firstName, lastName, email, "")
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
                authState.value = AuthState.Authenticated // estado autenticación exitosa
            } catch (e: Exception) {
                authState.value = AuthState.Error(e.localizedMessage ?: "Login error")
            }
        }
    }

    fun logout() {
        auth.signOut()
        authState.value = AuthState.Unauthenticated // estado no autenticado, error
    }

    fun toggleAuthMode() {
        _currentAuthMode.value = if (_currentAuthMode.value == AuthMode.Login) {
            AuthMode.Register
        } else {
            AuthMode.Login
        }
    }
    fun navigateToProfile() {
        _navigateToProfile.value = true
    }

    fun navigateToEditProfile() {
        _navigateToEditProfile.value = true
    }

    fun navigationHandled() {
        _navigateToProfile.value = false
        _navigateToEditProfile.value = false
    }
}


