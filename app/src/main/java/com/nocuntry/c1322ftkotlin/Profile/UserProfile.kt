package com.nocuntry.c1322ftkotlin.Profile

data class UserProfile(
    val firstName: String,
    val lastName: String,
    val email: String,
    val summary: String, // Resumen del usuario
    val followers: String, // Cambiado a String
    val following: String, // Cambiado a String
    val profileImageRes: Int // Cambiado a Int para referenciar un recurso de imagen
)


