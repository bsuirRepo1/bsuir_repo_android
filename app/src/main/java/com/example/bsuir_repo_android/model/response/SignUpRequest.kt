package com.example.bsuir_repo_android.model.response

data class SignUpRequest(
    val email: String,
    val username: String,
    val password: String,
    val password_confirm: String,
)
