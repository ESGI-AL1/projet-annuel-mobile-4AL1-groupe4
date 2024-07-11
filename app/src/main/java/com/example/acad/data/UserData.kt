package com.example.acad.data

import com.example.acad.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class UserData @Inject constructor() {

    private var _userState: MutableStateFlow<User> = MutableStateFlow(User())
    val userState: StateFlow<User> get() = _userState

    fun save(user: User) {
        _userState.value = user
    }
}