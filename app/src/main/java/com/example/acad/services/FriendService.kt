package com.example.acad.services

import com.example.acad.models.Friend
import com.example.acad.requests.FriendRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface FriendService {
    @GET("friends/")
    @Headers("Content-Type: application/json; charset=utf-8")
    suspend fun list(@Header("Authorization") token: String): List<Friend>

    @GET("friendships/")
    @Headers("Content-Type: application/json; charset=utf-8")
    suspend fun friendships(@Header("Authorization") token: String): List<Friend>

    @POST("friendships/")
    @Headers("Content-Type: application/json; charset=utf-8")
    suspend fun create(@Header("Authorization") token: String, @Body request: FriendRequest): Friend

    @POST("manage_friend_request/{friend_id}/{action}/")
    @Headers("Content-Type: application/json; charset=utf-8")
    suspend fun friendRequest(
        @Header("Authorization") token: String,
        @Path("friend_id") friendId: Long,
        @Path("action") action: String
    ): Any

}