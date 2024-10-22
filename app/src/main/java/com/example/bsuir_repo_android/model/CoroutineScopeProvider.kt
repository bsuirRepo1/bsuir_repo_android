package com.example.bsuir_repo_android.model

import kotlinx.coroutines.CoroutineScope

class CoroutineScopeProvider(private val coroutineScope: CoroutineScope? = null) {
    fun provide() = coroutineScope
}
