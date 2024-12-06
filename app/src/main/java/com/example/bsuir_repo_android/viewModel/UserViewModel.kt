package com.example.bsuir_repo_android.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bsuir_repo_android.BsuirRepoApplication
import com.example.bsuir_repo_android.repositories.UserRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class UserUiState(
    val email: String,
    val username: String,
    val password: String,
    val password_confirm: String,
)

class UserViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {
    val uiState: StateFlow<UserUiState> =
        userRepository.currentUser.map { user ->
            UserUiState(
                email = user.email,
                username = user.username,
                password = user.password,
                password_confirm = user.password_confirm,
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UserUiState("Unknown", "Unknown", "Unknown", "Unknown"),
        )

    fun saveUsername(username: String) {
        viewModelScope.launch {
            userRepository.saveUsername(username)
            Log.d("UserRepository", "Username saved: $username")
        }
    }

    fun saveUserEmail(email: String) {
        viewModelScope.launch {
            userRepository.saveUserEmail(email)
        }
    }

    fun saveUserPassword(password: String) {
        viewModelScope.launch {
            userRepository.saveUserPassword(password)
        }
    }

    fun saveUserPasswordConfirm(password_confirm: String) {
        viewModelScope.launch {
            userRepository.saveUserPasswordConfirm(password_confirm)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    val application = (this[APPLICATION_KEY] as BsuirRepoApplication)
                    val userRepository = application.container.userRepository // Доступ через container
                    UserViewModel(userRepository)
                }
            }
    }
}
