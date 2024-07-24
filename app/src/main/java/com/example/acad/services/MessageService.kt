package com.example.acad.services

import com.example.acad.models.Message
import com.example.acad.requests.MessageRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface MessageService {
    @GET("messages/")
    @Headers("Content-Type: application/json; charset=utf-8")
    suspend fun all(@Header("Authorization") token: String): List<Message>

    @POST("messages/")
    @Headers("Content-Type: application/json; charset=utf-8")
    suspend fun create(
        @Header("Authorization") token: String,
        @Body request: MessageRequest
    ): Message
}