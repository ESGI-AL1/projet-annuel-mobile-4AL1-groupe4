package com.example.acad.repositories

import com.example.acad.models.Action
import com.example.acad.models.Comment
import com.example.acad.requests.ActionRequest
import com.example.acad.requests.CommentRequest
import com.example.acad.services.CommentService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CommentRepository @Inject constructor(private val service: CommentService) {
    fun listComment(token: String): Flow<List<Comment>> = flow {
        emit(service.list(token = "Bearer $token"))
    }.flowOn(Dispatchers.IO)

    fun createComment(token: String, request: CommentRequest): Flow<Comment> = flow {
        emit(service.create(token = "Bearer $token", request = request))
    }.flowOn(Dispatchers.IO)

    fun addLike(token: String, request: ActionRequest): Flow<Action> = flow {
        emit(service.createLike(token = "Bearer $token", request = request))
    }.flowOn(Dispatchers.IO)
}