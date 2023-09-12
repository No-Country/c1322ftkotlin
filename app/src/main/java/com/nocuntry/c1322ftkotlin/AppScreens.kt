package com.nocuntry.c1322ftkotlin

sealed class AppScreens(val route: String) {
    object Home : AppScreens("home")
    object Detail : AppScreens("detail")
    object ZoomImage : AppScreens("ZoomImage")
    object Login : AppScreens("login")
    object Register : AppScreens("register")
    object Profile : AppScreens("profile")
    object EditProfile : AppScreens("editProfile")
    object ChatScreen : AppScreens("chatscreen")
    object Notifications : AppScreens("notifications")
    object Logout : AppScreens("logout")
    object CreatePost : AppScreens("create_post")

}


