package com.nocuntry.c1322ftkotlin.Profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val _userInfo = MutableStateFlow<UserInfo>(
        UserInfo(name = "Jualian Alvarez", email = "juliane@gmail.com")
    )

    val userInfo: StateFlow<UserInfo> = _userInfo

    fun updateUserInfo(newUserInfo: UserInfo) {
        _userInfo.value = newUserInfo
    }
}

data class UserInfo(val name: String, val email: String)
