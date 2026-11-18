package com.example.cauca_resilente_app.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import com.example.cauca_resilente_app.api.CaucaResilienteApiService
import com.example.cauca_resilente_app.api.SessionManager
import retrofit2.converter.gson.GsonConverterFactory

import java.util.concurrent.TimeUnit

object RetrofitInstance {
    private const val BASE_URL = "https://cauca-resiliente-api-production.up.railway.app/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val authInterceptor = okhttp3.Interceptor { chain ->
        val token = SessionManager.getToken()
        val request = if (!token.isNullOrBlank() && chain.request().header("Authorization") == null) {
            chain.request().newBuilder().addHeader("Authorization", token).build()
        } else {
            chain.request()
        }
        chain.proceed(request)
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    val apiService: CaucaResilienteApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CaucaResilienteApiService::class.java)
    }
}