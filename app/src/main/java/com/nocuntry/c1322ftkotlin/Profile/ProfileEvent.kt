package com.nocuntry.c1322ftkotlin.Profile

sealed class ProfileEvent {
    object ViewProfile : ProfileEvent()
    object EditProfile : ProfileEvent()
}
