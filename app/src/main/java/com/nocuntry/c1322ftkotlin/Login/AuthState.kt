package com.nocuntry.c1322ftkotlin.Login

sealed class AuthState {
    object None : AuthState()
    object Success : AuthState()
    data class Error(val errorMessage: String) : AuthState()
}

