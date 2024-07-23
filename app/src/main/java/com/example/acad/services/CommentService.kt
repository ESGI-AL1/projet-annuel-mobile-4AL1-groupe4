package com.example.acad.services

import com.example.acad.models.Action
import com.example.acad.models.Comment
import com.example.acad.requests.ActionRequest
import com.example.acad.requests.CommentRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface CommentService {
    @GET("comments/")
    @Headers("Content-Type: application/json; charset=utf-8")
    suspend fun list(@Header("Authorization") token: String): List<Comment>

    @POST("comments/")
    @Headers("Content-Type: application/json; charset=utf-8")
    suspend fun create(
        @Header("Authorization") token: String,
        @Body request: CommentRequest
    ): Comment

    @POST("actions/")
    @Headers("Content-Type: application/json; charset=utf-8")
    suspend fun createLike(
        @Header("Authorization") token: String,
        @Body request: ActionRequest
    ): Action

}