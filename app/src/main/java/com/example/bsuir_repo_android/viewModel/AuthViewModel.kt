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
import com.example.bsuir_repo_android.model.response.AuthResponse
import com.example.bsuir_repo_android.model.response.LoginRequest
import com.example.bsuir_repo_android.model.response.SignUpRequest
import com.example.bsuir_repo_android.repositories.AuthRepository
import com.example.bsuir_repo_android.repositories.Resource
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

val REGISTRATION = "login_vm"

data class AuthUserState(
    var email: String? = null,
    var username: String? = null,
    var password: String? = null,
    var passwordConfirm: String? = null,
    val isSignUp: Boolean = false,
    var isSuccess: Boolean = false,
    var error: String? = null,
    var emailEmpty: String? = null,
    var passwordEmpty: String? = null,
    var usernameEmpty: String? = null,
    var passwordConfirmEmpty: String? = null,
    var userData: AuthResponse? = null,
)

class AuthViewModel(
    private val authRepository: AuthRepository,
    private val userViewModel: UserViewModel,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val email = savedStateHandle.getStateFlow("email", "")
    private val username = savedStateHandle.getStateFlow("username", "")
    private val password = savedStateHandle.getStateFlow("password", "")
    private val passwordConfirm = savedStateHandle.getStateFlow("passwordConfirm", "")
    private val isSignUp = savedStateHandle.getStateFlow("isSignUp", false)

    private val _apiState = MutableStateFlow(AuthUserState())
    val apiState = _apiState.asStateFlow()

    val state =
        combine(email, username, password, passwordConfirm, isSignUp) { email, username, password, passwordConfirm, isSignUp ->
            AuthUserState(email = email, username = username, password = password, passwordConfirm = passwordConfirm, isSignUp = isSignUp)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AuthUserState())

    fun onEmailChanges(email: String) {
        savedStateHandle["email"] = email
    }

    fun onUsernameChanges(username: String) {
        savedStateHandle["username"] = username
    }

    fun onPasswordChanges(password: String) {
        savedStateHandle["password"] = password
    }

    fun onPasswordConfirmChanges(passwordConfirm: String) {
        savedStateHandle["passwordConfirm"] = passwordConfirm
    }

    fun toggleAuthMode() {
        savedStateHandle["isSignUp"] = !(state.value.isSignUp)
    }

    fun authUser() {
        Log.d("register_vm", "${email.value}, ${password.value}")

        val validationResult = UserValidator.validateAuthData(state.value)
        val error =
            listOfNotNull(
                validationResult.emailError,
                validationResult.usernameError,
                validationResult.passwordError,
                validationResult.passwordConfirmError,
            )

        if (error.isEmpty()) {
            viewModelScope.launch {
                _apiState.value = state.value

                if (state.value.isSignUp) {
                    val signUpRequest =
                        SignUpRequest(
                            username = state.value.username ?: "",
                            email = state.value.email ?: "",
                            password = state.value.password ?: "",
                            password_confirm = state.value.passwordConfirm ?: "",
                        )

                    Log.d("register_vm", "$signUpRequest")
                    authRepository.registerUser(signUpRequest).collectLatest { result ->
                        when (result) {
                            is Resource.Loading -> {
                                Log.d("register_vm", "Loading")
                            }

                            is Resource.Success -> {
                                _apiState.value =
                                    _apiState.value.copy(
                                        error = null,
                                        isSuccess = true,
                                        userData = result.data,
                                    )
                                Log.d(
                                    "register_vm",
                                    "${result.data?.email}, ${result.data?.username}, ${result.data?.password}, ${result.data?.passwordConfirm}",
                                )
                                result.data?.let {
                                    userViewModel.saveUserEmail(result.data.email)
                                    userViewModel.saveUsername(result.data.username)
                                    // userViewModel.saveUserPassword(result.data.password)
                                    // userViewModel.saveUserPasswordConfirm(result.data.password_confirm)
                                }
                                Log.d("register_vm", "Registered successfully")
                            }

                            is Resource.Error -> {
                                _apiState.value = _apiState.value.copy(error = result.message)

                                Log.w("register_vm", "Registration failed: ${result.message}")
                            }

                            else -> {
                                _apiState.value =
                                    _apiState.value.copy(
                                        isSuccess = false,
                                    )
                            }
                        }
                    }
                } else {
                    val loginRequest =
                        LoginRequest(
                            email = state.value.email ?: "",
                            password = state.value.password ?: "",
                        )

                    authRepository.loginUser(loginRequest).collectLatest { result ->
                        when (result) {
                            is Resource.Loading -> {
                                Log.d("register_vm", "Loading")
                            }

                            is Resource.Success -> {
                                _apiState.value =
                                    _apiState.value.copy(
                                        error = null,
                                        isSuccess = true,
                                        userData = result.data,
                                    )
                                Log.d(
                                    "register_vm",
                                    "1 ${result.data?.email}, ${result.data?.username}, ${result.data?.password}, ${result.data?.passwordConfirm}",
                                )
                                result.data?.let {
                                    userViewModel.saveUserEmail(result.data.email)
                                    // userViewModel.saveUsername(result.data.username)
                                    userViewModel.saveUserPassword(result.data.password)
                                    // userViewModel.saveUserPasswordConfirm(result.data.password_confirm)
                                }
                                Log.d("register_vm", "Registered successfully")
                                Log.d(
                                    "register_vm",
                                    "2 ${result.data?.email}, ${result.data?.username}, ${result.data?.password}, ${result.data?.passwordConfirm}",
                                )
                            }

                            is Resource.Error -> {
                                _apiState.value = _apiState.value.copy(error = result.message)

                                Log.w("register_vm", "Registration failed: ${result.message}")
                            }

                            else -> {
                                _apiState.value =
                                    _apiState.value.copy(
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
                    emailEmpty = validationResult.emailError,
                    usernameEmpty = validationResult.usernameError,
                    passwordEmpty = validationResult.passwordError,
                    passwordConfirmEmpty = validationResult.passwordConfirmError,
                    isSuccess = false,
                )
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BsuirRepoApplication
                    val authRepository = application.container.authRepository
                    val userRepository = application.container.userRepository // Добавляем UserRepository из контейнера
                    val userViewModel = UserViewModel(userRepository = userRepository)
                    AuthViewModel(authRepository = authRepository, userViewModel = userViewModel, savedStateHandle = SavedStateHandle())
                }
            }
    }
}

object UserValidator {
    fun validateAuthData(state: AuthUserState): AuthValidationResult {
        var result = AuthValidationResult()

        val email = state.email ?: ""
        val username = state.username ?: ""
        val password = state.password ?: ""
        val passwordConfirm = state.passwordConfirm ?: ""

        if (email.isBlank()) {
            result = result.copy(emailError = "The email can't be empty.")
        }

        val emailRegex = Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")
        if (!emailRegex.matches(email)) {
            result = result.copy(emailError = "This is not a valid email.")
        }

        if (username.isBlank()) {
            result = result.copy(usernameError = "The username can't be empty.")
        }
        // потворяющийся юзернейм
        // повторяющаяся почта

        if (password.length >= 0 && password.length < 8) {
            result = result.copy(passwordError = "The password must have at least 8 characters.")
        }

        if (passwordConfirm.isBlank()) {
            result = result.copy(passwordConfirmError = "The password confirmation can't be empty.")
        }

        if (password != state.passwordConfirm) {
            result = result.copy(passwordConfirmError = "The password and it's confirmation are not equal.")
        }

        return result
    }

    data class AuthValidationResult(
        val emailError: String? = null,
        val usernameError: String? = null,
        val passwordError: String? = null,
        val passwordConfirmError: String? = null,
    )
}

val AuthState = compositionLocalOf<AuthViewModel> { error("User State Context Not Found!") }
