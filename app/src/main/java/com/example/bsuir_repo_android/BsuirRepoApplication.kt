package com.example.bsuir_repo_android

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

object DataStoreProvider {
    private const val DATASTORE_NAME = "user_data"

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)
}

class BsuirRepoApplication : Application() {
    lateinit var container: BsuirRepoAppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(context = applicationContext)
    }
}
