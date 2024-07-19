package com.example.acad.services

import com.example.acad.models.Jwt
import com.example.acad.models.User
import com.example.acad.requests.LoginRequest
import com.example.acad.requests.UserRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthService {

    @POST("user/register/")
    @Headers("Content-Type: application/json; charset=utf-8")
    suspend fun register(@Body request: UserRequest): User

    @POST("token/")
    @Headers("Content-Type: application/json; charset=utf-8")
    suspend fun login(@Body request: LoginRequest): Jwt

    @GET("user/informations/{id}/")
    @Headers("Content-Type: application/json; charset=utf-8")
    suspend fun userInfo(@Header("Authorization") token: String, @Path("id") userId: String): User

    @GET("user/all/")
    @Headers("Content-Type: application/json; charset=utf-8")
    suspend fun list(@Header("Authorization") token: String): List<User>
}