package com.example.bsuir_repo_android

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.bsuir_repo_android.api.AuthApiService
import com.example.bsuir_repo_android.repositories.AuthRepository
import com.example.bsuir_repo_android.repositories.NetworkAuthRepository
import com.example.bsuir_repo_android.repositories.UserRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

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
