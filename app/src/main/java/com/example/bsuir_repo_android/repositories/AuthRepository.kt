package com.example.bsuir_repo_android.repositories

import android.util.Log
import com.example.bsuir_repo_android.api.AuthApiService
import com.example.bsuir_repo_android.model.response.LoginRequest
import com.example.bsuir_repo_android.model.response.LoginResponse
import com.example.bsuir_repo_android.model.response.UserReg
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.ResponseBody

// Api response Class replace 'T' with your desired response from Api
sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Loading<T>(data: T? = null) : Resource<T>(data)

    class Success<T>(data: T?) : Resource<T>(data)

    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
}

interface AuthRepository {
    fun loginUser(loginRequest: LoginRequest): Flow<Resource<LoginResponse>>

    fun registerUser(userReg: UserReg): Flow<Resource<ResponseBody>>
}

class NetworkAuthRepository(
    private val authApiService: AuthApiService,
) : AuthRepository {
    override fun registerUser(userReg: UserReg) =
        flow {
            emit(Resource.Loading())
            try {
                val response = authApiService.register(userReg).execute()
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(Resource.Success(it))
                    } ?: emit(Resource.Error("Empty response"))
                } else {
                    emit(Resource.Error("Registration failed: ${response.message()}"))
                }
            } catch (e: Exception) {
                Log.e("error_repo", e.toString())
                emit(Resource.Error("Something went wrong, please try again later!"))
            }
        }.flowOn(Dispatchers.IO)

    override fun loginUser(loginRequest: LoginRequest) =
        flow {
            emit(Resource.Loading())
            try {
                val response = authApiService.loginUser(loginRequest).execute()
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(Resource.Success(it))
                    } ?: emit(Resource.Error("Empty response"))
                } else {
                    emit(Resource.Error("Login failed: ${response.message()}"))
                }
            } catch (e: Exception) {
                Log.e("error_repo", e.toString())
                emit(Resource.Error("Something went wrong, please try again later!"))
            }
        }.flowOn(Dispatchers.IO)
}
