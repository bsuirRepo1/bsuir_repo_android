package com.example.bsuir_repo_android.api

import com.example.bsuir_repo_android.model.response.LoginRequest
import com.example.bsuir_repo_android.model.response.LoginResponse
import com.example.bsuir_repo_android.model.response.UserReg
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("register/")
    fun register(
        @Body userReg: UserReg,
    ): Call<ResponseBody>

    @POST("login/")
    fun loginUser(
        @Body loginRequest: LoginRequest,
    ): Call<LoginResponse>
}
