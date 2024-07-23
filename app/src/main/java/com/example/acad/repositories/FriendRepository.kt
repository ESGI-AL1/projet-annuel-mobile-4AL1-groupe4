package com.example.acad.repositories

import com.example.acad.models.Friend
import com.example.acad.requests.FriendRequest
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

    fun listFriends(token: String): Flow<List<Friend>> = flow {
        emit(service.friendships(token = "Bearer $token"))
    }.flowOn(Dispatchers.IO)

    fun createFriend(token: String, request: FriendRequest): Flow<Friend> = flow {
        emit(service.create(token = "Bearer $token", request = request))
    }.flowOn(Dispatchers.IO)

    fun sentFriendRequest(token: String, friendId: Long, action: String): Flow<Any> = flow {
        emit(service.friendRequest(token = "Bearer $token", friendId = 82, action = action))
    }.flowOn(Dispatchers.IO)

}