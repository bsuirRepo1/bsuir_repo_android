package com.example.bsuir_repo_android

import android.content.Context
import com.example.bsuir_repo_android.DataStoreProvider.dataStore
import com.example.bsuir_repo_android.api.AuthApiService
import com.example.bsuir_repo_android.repositories.AuthRepository
import com.example.bsuir_repo_android.repositories.NetworkAuthRepository
import com.example.bsuir_repo_android.repositories.UserRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface BsuirRepoAppContainer {
    val authRepository: AuthRepository
    val userRepository: UserRepository
}

class DefaultAppContainer(context: Context) : BsuirRepoAppContainer {
    private val baseUrl =
        "http://192.168.8.18:8000/api/users/"

    private val retrofit: Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()

    private val retrofitAuthService: AuthApiService by lazy {
        retrofit.create(AuthApiService::class.java)
    }

    override val authRepository: AuthRepository by lazy {
        NetworkAuthRepository(retrofitAuthService)
    }

    override val userRepository: UserRepository by lazy {
        UserRepository(context.dataStore)
    }
}
