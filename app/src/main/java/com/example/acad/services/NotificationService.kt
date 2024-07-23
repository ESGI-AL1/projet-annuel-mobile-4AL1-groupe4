package com.example.acad.services

import com.example.acad.models.Notification
import com.example.acad.requests.NotificationRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface NotificationService {
    @GET("notifications/")
    @Headers("Content-Type: application/json; charset=utf-8")
    suspend fun list(@Header("Authorization") token: String): List<Notification>

    @GET("notifications/{id}/")
    @Headers("Content-Type: application/json; charset=utf-8")
    suspend fun show(@Header("Authorization") token: String, @Path("id") id: String): Notification

    @POST("notifications/")
    @Headers("Content-Type: application/json; charset=utf-8")
    suspend fun create(token: String, @Body request: NotificationRequest): Notification
}