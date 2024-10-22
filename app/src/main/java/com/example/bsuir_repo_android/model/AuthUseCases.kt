package com.example.bsuir_repo_android.model

import android.util.Patterns

enum class UsernameValidationResult {
    IS_EMPTY,
    CORRECT,
}

class UsernameValidator {
    operator fun invoke(username: String): UsernameValidationResult {
        return if (username.isBlank()) {
            UsernameValidationResult.IS_EMPTY
        } else {
            UsernameValidationResult.CORRECT
        }
    }
}

enum class EmailValidationResult {
    INCORRECT_FORMAT,
    CORRECT,
}

class EmailValidator {
    operator fun invoke(email: String): EmailValidationResult {
        return if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            EmailValidationResult.CORRECT
        } else {
            EmailValidationResult.INCORRECT_FORMAT
        }
    }
}

enum class PasswordValidationResult {
    NOT_LONG_ENOUGH,
    NOT_ENOUGH_DIGITS,
    NOT_ENOUGH_UPPERCASE,
    CORRECT,
}

class PasswordValidator {
    operator fun invoke(password: String): PasswordValidationResult {
        return if (password.length < 8) {
            PasswordValidationResult.NOT_LONG_ENOUGH
        } else if (password.count(Char::isUpperCase) == 0) {
            PasswordValidationResult.NOT_ENOUGH_UPPERCASE
        } else if (!password.contains("[0-9]".toRegex())) {
            PasswordValidationResult.NOT_ENOUGH_DIGITS
        } else {
            PasswordValidationResult.CORRECT
        }
    }
}
