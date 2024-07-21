package com.example.acad.data

import android.content.ContentValues
import android.util.Log
import com.example.acad.models.Member
import com.example.acad.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class UserData @Inject constructor() {

    private var _userState: MutableStateFlow<User> = MutableStateFlow(User())
    private var _list: MutableList<User> = mutableListOf()
    val userState: StateFlow<User> get() = _userState
    val list: List<User> get() = _list

    fun save(user: User) {
        _userState.value = user
    }

    fun saveList(list: List<User>) {
        _list = list as MutableList<User>
    }

    fun getUser(index: Long): User? {
        Log.d(ContentValues.TAG, "getUser: $index")
        return list.find { user -> user.id.toLong() == index }
    }

    fun getUser(username: String): User? {
        return list.find { it.username == username }
    }
}

fun User.toMember(): Member = Member(
    id = id,
    username = "@$username",
    name = "$lastName $firstName",
    bio = "",
    imageResId = 0
)