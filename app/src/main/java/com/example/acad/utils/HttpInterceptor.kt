package com.example.acad.utils

import android.content.ContentValues
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class HttpInterceptor : Interceptor {

//    private val service = Retrofit.getRetrofit().create(StudentService::class.java)

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        Log.d(ContentValues.TAG, "request builder: ${requestBuilder.build()}")
        val response = chain.proceed(requestBuilder.build())
        Log.d(ContentValues.TAG, "response builder: ${response.body}")
        if (response.code == 401) {
            synchronized(this) {
                runBlocking {
                    launch {
                        refreshToken("hjhjmluji")
                    }
                }
//                repository.refreshToken(repository.refreshToken.value)
                Log.d(ContentValues.TAG, "refreshing token")
            }
        }
        return response
    }

    private fun refreshToken(token: String): Flow<Any> = flow {
        println("okhttp token: $token")
//        emit(service.refreshToken(token))
        emit("okhttp token: $token")
    }.flowOn(Dispatchers.IO)
}