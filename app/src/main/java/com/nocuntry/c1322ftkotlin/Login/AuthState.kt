package com.nocuntry.c1322ftkotlin.Login

sealed class AuthState {
    object None : AuthState()
    object Authenticated : AuthState()
    data class Error(val message: String) : AuthState()
    object Unauthenticated : AuthState()
    object Success : AuthState()
}
