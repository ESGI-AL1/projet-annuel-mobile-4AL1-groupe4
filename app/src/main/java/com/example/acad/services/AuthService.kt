package com.example.acad.services

import com.example.acad.models.Jwt
import com.example.acad.models.User
import com.example.acad.requests.LoginRequest
import com.example.acad.requests.UserRequest
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthService {

    @POST("user/register/")
    @Headers("Content-Type: application/json; charset=utf-8")
    suspend fun register(@Body request: UserRequest): User

    @POST("/api/token/")
    @Headers("Content-Type: application/json; charset=utf-8")
    suspend fun login(@Body request: LoginRequest): Jwt
}