package com.example.bsuir_repo_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.bsuir_repo_android.ui.BsuirRepoAndroidApp
import com.example.bsuir_repo_android.ui.theme.Bsuir_repo_androidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Bsuir_repo_androidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val my_padding = innerPadding
                    BsuirRepoAndroidApp()
                }
            }
        }
    }
}
