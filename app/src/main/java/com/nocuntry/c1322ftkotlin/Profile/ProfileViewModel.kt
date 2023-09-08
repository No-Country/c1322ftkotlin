package com.nocuntry.c1322ftkotlin.Profile

import UserProfile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val _userInfo = MutableStateFlow(UserInfo())

    val userInfo: StateFlow<UserInfo> = _userInfo

    fun loadProfileData(userProfile: UserProfile) {
        val newUserInfo = UserInfo(
            name = "${userProfile.firstName} ${userProfile.lastName}",
            email = userProfile.email
        )
        _userInfo.value = newUserInfo
    }

    fun updateUserInfo(newUserInfo: UserInfo) {
        _userInfo.value = newUserInfo
    }
}

data class UserInfo(
    val name: String = "",
    val email: String = ""
)
