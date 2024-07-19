package com.example.acad.repositories

import com.example.acad.models.Friend
import com.example.acad.services.FriendService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FriendRepository @Inject constructor(private val service: FriendService) {
    fun listFriend(token: String): Flow<List<Friend>> = flow {
        emit(service.list(token = "Bearer $token"))
    }.flowOn(Dispatchers.IO)
}