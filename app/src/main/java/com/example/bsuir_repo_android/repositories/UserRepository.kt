package com.example.bsuir_repo_android.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.bsuir_repo_android.viewModel.UserUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepository(private val dataStore: DataStore<Preferences>) {
    private companion object {
        val USER_EMAIL = stringPreferencesKey("user_email")
        val USER_NAME = stringPreferencesKey("user_name")
        val USER_PASSWORD = stringPreferencesKey("user_password")
        val USER_PASSWORD_CONFIRM = stringPreferencesKey("user_password")
    }

    val currentUser: Flow<UserUiState> =
        dataStore.data.map { preferences ->
            UserUiState(
                username = preferences[USER_NAME] ?: "Unknown",
                email = preferences[USER_EMAIL] ?: "Unknown",
                password = preferences[USER_PASSWORD] ?: "Unknown",
                password_confirm = preferences[USER_PASSWORD_CONFIRM] ?: "Unknown",
            )
        }

    suspend fun saveUserName(userName: String) {
        dataStore.edit { preferences ->
            preferences[USER_NAME] = userName
        }
    }

    suspend fun saveUserEmail(userEmail: String) {
        dataStore.edit { preferences ->
            preferences[USER_EMAIL] = userEmail
        }
    }

    suspend fun saveUserPassword(userPassword: String) {
        dataStore.edit { preferences ->
            preferences[USER_PASSWORD] = userPassword
        }
    }

    suspend fun saveUserPasswordConfirm(userPasswordConfirm: String) {
        dataStore.edit { preferences ->
            preferences[USER_PASSWORD_CONFIRM] = userPasswordConfirm
        }
    }
}
