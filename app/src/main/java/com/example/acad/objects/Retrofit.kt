package com.example.acad.objects

package com.example.acad.objects

import com.google.gson.GsonBuilder
import com.example.acad.utils.HttpInterceptor
import com.example.acad.utils.SERVER_IP_BASE
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Retrofit {

    private const val BASE_URL = "http://$SERVER_IP_BASE/api/"

    private val okHttpClient = OkHttpClient().newBuilder()
        .followRedirects(true)
        .addInterceptor(HttpInterceptor())
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .build()

    private val gson = GsonBuilder()
        .setLenient()
        .create()

    fun getRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .client(okHttpClient)
        .build()
}