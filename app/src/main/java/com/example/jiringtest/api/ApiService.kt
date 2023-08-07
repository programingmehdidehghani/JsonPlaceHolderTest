package com.example.jiringtest.api

import com.example.jiringtest.model.LoginResponse
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("users")
    suspend fun login(
        @Query("username") username: String,
    ): Response<LoginResponse>
}