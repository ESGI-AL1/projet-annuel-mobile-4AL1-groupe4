package com.example.acad.repositories

import com.example.acad.models.Jwt
import com.example.acad.models.User
import com.example.acad.requests.LoginRequest
import com.example.acad.requests.UserRequest
import com.example.acad.services.AuthService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val service: AuthService
) {

    fun registerUser(request: UserRequest): Flow<User> = flow {
        emit(service.register(request))
    }.flowOn(Dispatchers.IO)

    fun loginUser(request: LoginRequest): Flow<Jwt> = flow {
        emit(service.login(request))
    }.flowOn(Dispatchers.IO)

    fun getAuthUser(token: String, userId: String): Flow<User> = flow {
        emit(service.userInfo("Bearer $token", userId))
    }.flowOn(Dispatchers.IO)

    fun allUsers(token: String): Flow<List<User>> = flow {
        emit(service.list("Bearer $token"))
    }.flowOn(Dispatchers.IO)
}