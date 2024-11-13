package com.example.bsuir_repo_android.viewModel

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
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class UserUiState(
    val username: String,
    val email: String,
    val password: String,
    val password_confirm: String,
)

class UserViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {
    val uiState: StateFlow<UserUiState> =
        userRepository.currentUser.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UserUiState("Unknown", "Unknown", "Unknown", "Unknown"),
        )

    fun saveUserName(username: String) {
        viewModelScope.launch {
            userRepository.saveUserName(username)
        }
    }

    fun saveUserEmail(userEmail: String) {
        viewModelScope.launch {
            userRepository.saveUserEmail(userEmail)
        }
    }

    fun saveUserPassword(userPassword: String) {
        viewModelScope.launch {
            userRepository.saveUserPassword(userPassword)
        }
    }

    fun saveUserPasswordConfirm(userPasswordConfirm: String) {
        viewModelScope.launch {
            userRepository.saveUserPasswordConfirm(userPasswordConfirm)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    val application = (this[APPLICATION_KEY] as BsuirRepoApplication)
                    UserViewModel(application.userRepository)
                }
            }
    }
}
