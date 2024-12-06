package com.example.bsuir_repo_android.repositories

import android.util.Log
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
        val USER_PASSWORD_CONFIRM = stringPreferencesKey("user_password_confirm")
    }

    // catch reading errors
    val currentUser: Flow<UserUiState> =
        dataStore.data.map { preferences ->
            Log.d("UserRepository", "${preferences[USER_NAME]}")
            UserUiState(
                username = preferences[USER_NAME] ?: "Unknown",
                email = preferences[USER_EMAIL] ?: "Unknown",
                password = preferences[USER_PASSWORD] ?: "Unknown",
                password_confirm = preferences[USER_PASSWORD_CONFIRM] ?: "Unknown",
            )
        }

    suspend fun saveUsername(username: String) {
        dataStore.edit { preferences ->
            preferences[USER_NAME] = username
        }
    }

    suspend fun saveUserEmail(email: String) {
        dataStore.edit { preferences ->
            preferences[USER_EMAIL] = email
        }
    }

    suspend fun saveUserPassword(password: String) {
        dataStore.edit { preferences ->
            preferences[USER_PASSWORD] = password
        }
    }

    suspend fun saveUserPasswordConfirm(password_confirm: String) {
        dataStore.edit { preferences ->
            preferences[USER_PASSWORD_CONFIRM] = password_confirm
        }
    }
}
