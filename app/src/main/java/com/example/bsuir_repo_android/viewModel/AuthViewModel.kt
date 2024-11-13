package com.example.bsuir_repo_android.viewModel

import android.util.Log
import androidx.compose.runtime.compositionLocalOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bsuir_repo_android.BsuirRepoApplication
import com.example.bsuir_repo_android.model.response.LoginRequest
import com.example.bsuir_repo_android.model.response.LoginResponse
import com.example.bsuir_repo_android.model.response.UserReg
import com.example.bsuir_repo_android.repositories.AuthRepository
import com.example.bsuir_repo_android.repositories.Resource
import com.example.bsuir_repo_android.repositories.UserRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

val REGISTRATION = "login_vm"
data class AuthUserState(
    var email: String? = null,
    var password: String? = null,
    var error: String? = null,
    var emailEmpty: String? = null,
    var passwordEmpty: String? = null,
    var userData: LoginResponse? = null,
    var isSuccess: Boolean = false,
)

class AuthViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val email = savedStateHandle.getStateFlow("email", "")
    private val password = savedStateHandle.getStateFlow("password", "")

    private val _apiState = MutableStateFlow(AuthUserState())
    val apiState = _apiState.asStateFlow()

    val state =
        combine(email, password) { email, password ->
            AuthUserState(email = email, password = password)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AuthUserState())

    fun onEmailChanges(email: String) {
        savedStateHandle["email"] = email
    }

    fun onPasswordChanges(password: String) {
        savedStateHandle["password"] = password
    }

    // Сохранение данных пользователя в DataStore после успешного входа в систему
    private suspend fun saveUserData(userData: LoginResponse) {
        userRepository.saveUserEmail(userData.email ?: "Unknown")
        userRepository.saveUserPassword(userData.password ?: "Unknown")
    }

    fun loginUser() {
        val loginRequest =
            LoginRequest(
                email = state.value.email ?: "",
                password = state.value.password ?: "",
            )

        val results = UserValidator.validateLoginData(loginRequest)
        val error =
            listOfNotNull(
                results.emailError,
                results.passwordError,
            )

        if (error.isEmpty()) {
            viewModelScope.launch {
                _apiState.value = state.value
                authRepository.loginUser(loginRequest).collectLatest {
                    when (it) {
                        is Resource.Error -> {
                            _apiState.value = _apiState.value.copy(error = it.message)
                        }
                        is Resource.Loading -> {
                            Log.d("login_vm", "Loading")
                        }
                        is Resource.Success -> {
                            val userData = it.data
                            // Проверяем, что email и password не пустые
                            if (userData != null && userData.email.isNotEmpty() && userData.password.isNotEmpty()) {
                                _apiState.value =
                                    _apiState.value.copy(
                                        error = null,
                                        isSuccess = true,
                                        userData = userData,
                                    )
                                // Сохраняем данные пользователя в DataStore
                                saveUserData(userData)
                            } else {
                                _apiState.value =
                                    _apiState.value.copy(
                                        error = "Login failed.",
                                        isSuccess = false,
                                    )
                            }
                        }
                    }
                }
            }
        } else {
            _apiState.value =
                _apiState.value.copy(
                    emailEmpty = results.emailError,
                    passwordEmpty = results.passwordError,
                    isSuccess = false,
                )
        }
    }

    fun registerUser(userReg: UserReg) {
        viewModelScope.launch {
            _apiState.value = AuthUserState() // Обновляем состояние перед началом регистрации
            authRepository.registerUser(userReg).collectLatest {
                when (it) {
                    is Resource.Loading -> Log.d("register_vm", "Loading")
                    is Resource.Success -> {
                        _apiState.value =
                            _apiState.value.copy(
                                isSuccess = true,
                                error = null,
                            )
                        Log.d("register_vm", "Registered successfully")
                    }
                    is Resource.Error -> {
                        _apiState.value = _apiState.value.copy(error = it.message)
                        Log.w("register_vm", "Registration failed: ${it.message}")
                    }
                }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BsuirRepoApplication
                    val authRepository = application.container.authRepository
                    val userRepository = application.container.userRepository // Добавляем UserRepository из контейнера
                    AuthViewModel(authRepository = authRepository, userRepository = userRepository, savedStateHandle = SavedStateHandle())
                }
            }
    }
}

object UserValidator {
    fun validateLoginData(contact: LoginRequest): LoginValidationResult {
        var result = LoginValidationResult()

        if (contact.email.isBlank()) {
            result = result.copy(emailError = "The email can't be empty.")
        }

        val emailRegex = Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")
        if (!emailRegex.matches(contact.email)) {
            result = result.copy(emailError = "This is not a valid email.")
        }

        if (contact.password.isBlank()) {
            result = result.copy(passwordError = "The password can't be empty.")
        }

        return result
    }

    data class LoginValidationResult(
        val emailError: String? = null,
        val passwordError: String? = null,
    )
}

val AuthState = compositionLocalOf<AuthViewModel> { error("User State Context Not Found!") }
