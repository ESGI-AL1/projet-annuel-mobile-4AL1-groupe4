package com.example.acad.services

import com.example.acad.models.Friend
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface FriendService {
    @GET("friends/")
    @Headers("Content-Type: application/json; charset=utf-8")
    suspend fun list(@Header("Authorization") token: String): List<Friend>
}