package com.example.acad.repositories

import com.example.acad.models.Message
import com.example.acad.requests.MessageRequest
import com.example.acad.services.MessageService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MessageRepository @Inject constructor(private val service: MessageService) {
    fun allMessage(token: String): Flow<List<Message>> = flow {
        emit(service.all(token = "Bearer $token"))
    }.flowOn(Dispatchers.IO)

    fun addMessage(token: String, request: MessageRequest): Flow<Message> = flow {
        emit(service.create(token = "Bearer $token", request))
    }.flowOn(Dispatchers.IO)
}