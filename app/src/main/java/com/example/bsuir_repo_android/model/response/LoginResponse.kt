package com.example.bsuir_repo_android.model.response

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val email: String,
    val password: String,
)
