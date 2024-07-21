package com.example.acad.repositories

import android.util.Log
import com.example.acad.models.Program
import com.example.acad.requests.ProgramRequest
import com.example.acad.services.ProgramService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
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

    fun createProgram(token: String, request: ProgramRequest): Flow<Any> = flow {
        // Créer les RequestBody pour les champs texte
        val titlePart = request.title.toRequestBody("text/plain".toMediaTypeOrNull())
        val descriptionPart = request.description.toRequestBody("text/plain".toMediaTypeOrNull())

        // Créer le RequestBody pour le fichier
        val filePart = request.file?.let { file ->
            Log.d("Upload", "File name: ${file.name}")
            MultipartBody.Part.createFormData(
                "file",
                file.name,
                file.asRequestBody("application/octet-stream".toMediaTypeOrNull())
            )
        }
        emit(
            service.create(
                token = "Bearer $token", title = titlePart, description = descriptionPart,
                file = filePart
            )
        )
    }.flowOn(Dispatchers.IO)

    fun getProgram(token: String, programId: String): Flow<Program> = flow {
        emit(service.show("Bearer $token", programId))
    }.flowOn(Dispatchers.IO)
}