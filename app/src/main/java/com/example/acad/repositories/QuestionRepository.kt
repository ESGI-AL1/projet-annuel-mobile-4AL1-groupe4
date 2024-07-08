package com.example.acad.repositories

import com.example.acad.models.Question
import com.example.acad.requests.QuestionRequest
import com.example.acad.services.QuestionService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class QuestionRepository @Inject constructor(private val service: QuestionService) {
    fun groupQuestionList(token: String, groupId: String): Flow<List<Question>> = flow {
        emit(service.group("Bearer $token", groupId))
    }.flowOn(Dispatchers.IO)

    fun meQuestionList(token: String): Flow<List<Question>> = flow {
        emit(service.me("Bearer $token"))
    }.flowOn(Dispatchers.IO)

    fun questionList(token: String): Flow<List<Question>> = flow {
        emit(service.all("Bearer $token"))
    }.flowOn(Dispatchers.IO)

    fun createQuestion(token: String, groupId: String, request: QuestionRequest): Flow<Question> =
        flow {
            emit(service.create("Bearer $token", groupId, request))
        }.flowOn(Dispatchers.IO)
}