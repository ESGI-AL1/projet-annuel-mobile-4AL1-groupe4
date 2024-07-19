package com.example.acad.services

import com.example.acad.models.Program
import com.example.acad.models.ShowProgram
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ProgramService {

    @GET("/api/programs/")
    @Headers("Content-Type: application/json; charset=utf-8")
    suspend fun list(@Header("Authorization") token: String): List<Program>

    @GET("/api/programs/public/all/")
    @Headers("Content-Type: application/json; charset=utf-8")
    suspend fun publicList(@Header("Authorization") token: String): List<Program>

    @Multipart
    @POST("/api/programs/")
    suspend fun create(
        @Header("Authorization") token: String,
        @Part("title") title: RequestBody,
        @Part("description") description: RequestBody,
//        @Part("file") file: MultipartBody.Part
    ): Program

    @GET("/api/programs/{id}/")
    @Headers("Content-Type: application/json; charset=utf-8")
    suspend fun show(@Header("Authorization") token: String, @Path("id") programId: String): Program
}