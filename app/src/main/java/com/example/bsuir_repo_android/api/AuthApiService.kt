package com.example.bsuir_repo_android.api

import com.example.bsuir_repo_android.model.response.AuthResponse
import com.example.bsuir_repo_android.model.response.LoginRequest
import com.example.bsuir_repo_android.model.response.SignUpRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("register")
    suspend fun register(
        @Body signUpRequest: SignUpRequest,
    ): Response<AuthResponse>

    @POST("login")
    suspend fun loginUser(
        @Body loginRequest: LoginRequest,
    ): Response<AuthResponse>
}
