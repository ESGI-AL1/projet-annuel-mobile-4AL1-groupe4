package com.example.acad.services

import com.example.acad.models.Question
import com.example.acad.requests.QuestionRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface QuestionService {

    @GET("groups/{groupId}/questions/")
    @Headers("Content-Type: application/json; charset=utf-8")
    suspend fun group(
        @Header("Authorization") token: String,
        @Path("groupId") groupId: String
    ): List<Question>

    @GET("groups/questions/me/")
    @Headers("Content-Type: application/json; charset=utf-8")
    suspend fun me(
        @Header("Authorization") token: String
    ): List<Question>

    @GET("groups/questions/")
    @Headers("Content-Type: application/json; charset=utf-8")
    suspend fun all(
        @Header("Authorization") token: String
    ): List<Question>

    @POST("groups/{groupId}/questions/")
    @Headers("Content-Type: application/json; charset=utf-8")
    suspend fun create(
        @Header("Authorization") token: String, @Path("groupId") groupId: String,
        @Body request: QuestionRequest
    ): Question
}