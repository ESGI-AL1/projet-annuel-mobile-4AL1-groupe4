package com.example.acad.services

import com.example.acad.models.Group
import com.example.acad.requests.GroupRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface GroupService {

    @GET("groups/")
    @Headers("Content-Type: application/json; charset=utf-8")
    suspend fun list(@Header("Authorization") token: String): List<Group>

    @GET("groups/public/all/")
    @Headers("Content-Type: application/json; charset=utf-8")
    suspend fun publicList(@Header("Authorization") token: String): List<Group>

    @POST("groups/")
    @Headers("Content-Type: application/json; charset=utf-8")
    suspend fun create(@Header("Authorization") token: String, @Body request: GroupRequest): Group

    @GET("groups/{id}/")
    @Headers("Content-Type: application/json; charset=utf-8")
    suspend fun show(@Header("Authorization") token: String, @Path("id") groupId: String?): Group
}