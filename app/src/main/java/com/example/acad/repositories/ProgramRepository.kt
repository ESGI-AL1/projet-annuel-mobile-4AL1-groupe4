package com.example.acad.repositories

import com.example.acad.models.Program
import com.example.acad.models.ShowProgram
import com.example.acad.requests.ProgramRequest
import com.example.acad.services.ProgramService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType
import okhttp3.RequestBody
import javax.inject.Inject

class ProgramRepository @Inject constructor(
    private val service: ProgramService
) {

    fun listProgram(token: String): Flow<List<Program>> = flow {
        emit(service.list(token = "Bearer $token"))
    }.flowOn(Dispatchers.IO)

    fun publicListProgram(token: String): Flow<List<Program>> = flow {
        emit(service.publicList(token = "Bearer $token"))
    }.flowOn(Dispatchers.IO)

    fun createProgram(token: String, request: ProgramRequest): Flow<Program> = flow {
        emit(
            service.create(
                token = "Bearer $token",
                title = RequestBody.create(MediaType.parse("text/plain"), request.title),
                description = RequestBody.create(
                    MediaType.parse("text/plain"),
                    request.description
                ),
                tags = RequestBody.create(
                    MediaType.parse("text/plain"),
                    listOf(request.tags).toString()
                )
            )
        )
    }.flowOn(Dispatchers.IO)

    fun getProgram(token: String, programId: String): Flow<ShowProgram> = flow {
        emit(service.show("Bearer $token", programId))
    }.flowOn(Dispatchers.IO)
}