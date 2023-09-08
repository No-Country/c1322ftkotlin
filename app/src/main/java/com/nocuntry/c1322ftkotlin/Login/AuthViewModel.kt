package com.nocuntry.c1322ftkotlin.Login

import UserProfile
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.nocuntry.c1322ftkotlin.AppScreens
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.nocuntry.c1322ftkotlin.Login.AuthState
import com.nocuntry.c1322ftkotlin.Profile.ProfileEvent
import com.nocuntry.c1322ftkotlin.R


class AuthViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    private val _loading = MutableLiveData(false)

    private val _navigateToProfile = mutableStateOf(false)
    val navigateToProfile: State<Boolean> = _navigateToProfile

    private val _navigateToEditProfile = mutableStateOf(false)
    val navigateToEditProfile: State<Boolean> = _navigateToEditProfile

    private val _currentAuthMode = mutableStateOf(AuthMode.Login)
    val currentAuthMode: State<AuthMode> = _currentAuthMode

    val authState = mutableStateOf<AuthState>(AuthState.None)
    var authMode: AuthMode = AuthMode.Login

    val currentScreen: MutableState<AppScreens> = mutableStateOf(AppScreens.Login)
    val profileEvent: MutableState<ProfileEvent?> = mutableStateOf(null)

    private val _userProfile = mutableStateOf<UserProfile?>(null)
    val userProfile: State<UserProfile?> = _userProfile

    private val _isUserAuthenticated = mutableStateOf(false)
    val isUserAuthenticated: State<Boolean> = _isUserAuthenticated

    fun signInWithGoogle(credential: AuthCredential, home: () -> Unit) {
        viewModelScope.launch {
            try {
                auth.signInWithCredential(credential).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("SkyWonders ", "Logueado éxitoso con Google")
                        home()
                    }
                }.addOnFailureListener {
                    Log.d("SkyWonders ", "Error al loguear con Google")
                }
            } catch (ex: Exception) {
                Log.d("SkyWonders ", "Excepción al loguear con Google")
                authState.value = AuthState.Error("${ex.localizedMessage}")
            }
        }
    }

    fun register(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {
        if (password != confirmPassword) {
            authState.value = AuthState.Error("Passwords do not match")
            return
        }

        viewModelScope.launch {
            try {
                auth.createUserWithEmailAndPassword(email, password)
                val followers = 0 // Valor predeterminado para followers
                val following = 0 // Valor predeterminado para following

                _userProfile.value = UserProfile(
                    firstName,
                    lastName,
                    email,
                    "hello",
                    "0",
                    "0",
                    R.drawable.baseline_account_circle_24
                )

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
                authState.value = AuthState.Authenticated
            } catch (e: Exception) {
                authState.value = AuthState.Error(e.localizedMessage ?: "Login error")
            }
        }
    }

    fun onUserAuthenticated() {
        _isUserAuthenticated.value = true
    }

    fun logout() {
        auth.signOut()
        authState.value = AuthState.Unauthenticated
        _isUserAuthenticated.value = false
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

