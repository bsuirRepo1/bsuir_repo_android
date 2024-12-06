package com.example.bsuir_repo_android.repositories

import android.util.Log
import com.example.bsuir_repo_android.api.AuthApiService
import com.example.bsuir_repo_android.model.response.AuthResponse
import com.example.bsuir_repo_android.model.response.LoginRequest
import com.example.bsuir_repo_android.model.response.SignUpRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

// Api response Class replace 'T' with your desired response from Api
sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Loading<T>(data: T? = null) : Resource<T>(data)

    class Success<T>(data: T?) : Resource<T>(data)

    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
}

interface AuthRepository {
    fun loginUser(loginRequest: LoginRequest): Flow<Resource<AuthResponse>>

    fun registerUser(signUpRequest: SignUpRequest): Flow<Resource<AuthResponse>>
}

class NetworkAuthRepository(
    private val authApiService: AuthApiService,
) : AuthRepository {
    fun <T> handleResponse(response: Response<T>): Resource<T> {
        return if (response.isSuccessful) {
            response.body()?.let { Resource.Success(it) }
                ?: Resource.Error("Empty response")
        } else {
            Resource.Error("Error: ${response.message()}")
        }
    }

    override fun registerUser(signUpRequest: SignUpRequest) =
        flow {
            emit(Resource.Loading<AuthResponse>())
            try {
                authApiService.register(signUpRequest).let {
                    val response = handleResponse(it)
                    emit(response)
                }
            } catch (e: Exception) {
                Log.e("error_repo", e.message ?: "Unknown error")
                emit(Resource.Error("Something went wrong, Please try again later!"))
            }
        }.flowOn(Dispatchers.IO)

    override fun loginUser(loginRequest: LoginRequest) =
        flow {
            emit(Resource.Loading<AuthResponse>())
            try {
                authApiService.loginUser(loginRequest).let {
                    val response = handleResponse(it)
                    emit(response)
                }
            } catch (e: Exception) {
                Log.e("error_repo", e.message ?: "Unknown error")
                emit(Resource.Error("Something went wrong, please try again later!"))
            }
        }.flowOn(Dispatchers.IO)
}
