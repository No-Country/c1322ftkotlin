package com.nocuntry.c1322ftkotlin

sealed class AppScreens(val route: String) {
    object Home : AppScreens("home")
    object Detail : AppScreens("detail")
    object ZoomImage : AppScreens("ZoomImage")
    object Login : AppScreens("login")
    object Profile : AppScreens("profile")
    object EditProfile : AppScreens("editProfile")

}


