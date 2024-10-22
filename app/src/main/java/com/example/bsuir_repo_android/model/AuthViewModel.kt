package com.example.bsuir_repo_android.model

/*
@HiltViewModel
class AuthViewModel
    @Inject
    constructor(
        private val authRepository: AuthRepository,
        coroutineScopeProvider: CoroutineScopeProvider,
    ) : ViewModel() {
        private val usernameValidator = UsernameValidator()
        private val emailValidator = EmailValidator()
        private val passwordValidator = PasswordValidator()
        private val scope = coroutineScopeProvider.provide() ?: viewModelScope
        private val _uiState = MutableStateFlow(UiState())
        val uiState = _uiState.asStateFlow()

        fun onUsername(username: String) {
            val result =
                when (usernameValidator(username)) {
                    UsernameValidationResult.IS_EMPTY -> StringValue.StringResource(R.string.username_not_long_enough)
                    else -> StringValue.Empty
                }
            _uiState.update {
                it.copy(
                    validationState = it.validationState.copy(usernameValidationError = result),
                    authState = it.authState.copy(username = username),
                )
            }
        }

        fun onEmail(email: String) {
            val result =
                when (emailValidator(email)) {
                    EmailValidationResult.INCORRECT_FORMAT -> StringValue.StringResource(R.string.invalid_email_format)
                    else -> StringValue.Empty
                }
            _uiState.update {
                it.copy(
                    validationState = it.validationState.copy(emailValidationError = result),
                    authState = it.authState.copy(email = email),
                )
            }
        }

        fun onPassword(password: String) {
            val result =
                when (passwordValidator(password)) {
                    PasswordValidationResult.NOT_LONG_ENOUGH -> StringValue.StringResource(R.string.password_not_long_enough)
                    PasswordValidationResult.NOT_ENOUGH_UPPERCASE -> StringValue.StringResource(R.string.password_not_enough_uppercase)
                    PasswordValidationResult.NOT_ENOUGH_DIGITS -> StringValue.StringResource(R.string.password_not_enough_digits)
                    else -> StringValue.Empty
                }
            _uiState.update {
                it.copy(
                    validationState = it.validationState.copy(passwordValidationError = result),
                    authState = it.authState.copy(password = password),
                )
            }
        }

        data class UiState(
            val authType: AuthType = AuthType.SIGN_IN,
            val authState: AuthState = AuthState(),
            val validationState: ValidationState = ValidationState(),
            val authResult: CustomResult = CustomResult.Idle,
        )

        data class ValidationState(
            val usernameValidationError: StringValue = StringValue.Empty,
            val emailValidationError: StringValue = StringValue.Empty,
            val passwordValidationError: StringValue = StringValue.Empty,
        )
    }

data class AuthState(
    val username: String? = null,
    val email: String? = null,
    val password: String? = null,
)

enum class AuthType {
    SIGN_IN,
    SIGN_UP,
}

enum class FieldType {
    USERNAME,
    EMAIL,
    PASSWORD,
}
*/
