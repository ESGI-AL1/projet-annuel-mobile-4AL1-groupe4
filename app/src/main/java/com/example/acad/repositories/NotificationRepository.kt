package com.example.acad.repositories

import com.example.acad.models.Notification
import com.example.acad.requests.NotificationRequest
import com.example.acad.services.NotificationService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NotificationRepository @Inject constructor(private val service: NotificationService) {
    fun listNotification(token: String): Flow<List<Notification>> = flow {
        emit(service.list(token = "Bearer $token"))
    }.flowOn(Dispatchers.IO)

    fun showNotification(token: String, id: String): Flow<Notification> = flow {
        emit(service.show(token = "Bearer $token", id = id))
    }.flowOn(Dispatchers.IO)

    fun createNotification(token: String, request: NotificationRequest): Flow<Notification> = flow {
        emit(service.create(token = "Bearer $token", request = request))
    }.flowOn(Dispatchers.IO)
}