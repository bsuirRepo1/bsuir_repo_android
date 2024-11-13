package com.example.bsuir_repo_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.bsuir_repo_android.ui.theme.Bsuir_repo_androidTheme
import com.example.bsuir_repo_android.viewModel.AuthState
import com.example.bsuir_repo_android.viewModel.AuthViewModel

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Bsuir_repo_androidTheme {
                navController = rememberNavController()
                val authViewModel: AuthViewModel =
                    viewModel(factory = AuthViewModel.Factory)

                CompositionLocalProvider(AuthState provides authViewModel) {
                    ApplicationSwitcher(navController = navController)
                }
            }
        }
    }
}
