package com.example.bsuir_repo_android.model.response

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val email: String,
    val username: String,
    val password: String,
    val passwordConfirm: String,
)
