package com.example.bsuir_repo_android

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.bsuir_repo_android.repositories.UserRepository

private val Context.datastore: DataStore<Preferences> by preferencesDataStore(
    name = "settings",
)

class BsuirRepoApplication : Application() {
    lateinit var container: BsuirRepoAppContainer
    lateinit var userRepository: UserRepository

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(context = applicationContext)
        userRepository = UserRepository(datastore)
    }
}
