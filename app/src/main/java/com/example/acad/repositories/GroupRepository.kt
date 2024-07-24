package com.example.acad.repositories

import android.content.ContentValues
import android.util.Log
import com.example.acad.models.Group
import com.example.acad.requests.GroupRequest
import com.example.acad.services.GroupService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GroupRepository @Inject constructor(private val service: GroupService) {
    fun listProgram(token: String): Flow<List<Group>> = flow {
        emit(service.list(token = "Bearer $token"))
    }.flowOn(Dispatchers.IO)

    fun publicListGroup(token: String): Flow<List<Group>> = flow {
        emit(service.publicList(token = "Bearer $token"))
    }.flowOn(Dispatchers.IO)

    fun createGroup(token: String, request: GroupRequest): Flow<Group> = flow {
        emit(service.create(token = "Bearer $token", request))
    }.flowOn(Dispatchers.IO)

    fun getGroup(token: String, groupId: String?): Flow<Group> = flow {
        try {
            val response = service.show(token = "Bearer $token", groupId)
            emit(response)
        } catch (e: Exception) {
            Log.d(ContentValues.TAG, "getGroup: ${e.message}")
        }
    }.flowOn(Dispatchers.IO)
}