package com.example.bsuir_repo_android.ui.app_screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.bsuir_repo_android.model.navigation.ScreenRoute

@Composable
fun ProfileScreen(
    navController: NavController,
    modifier: Modifier,
) {
    Column(modifier = modifier) {
        Text(text = "ProfileScreen")
        Button(onClick = { navController.navigate(ScreenRoute.UserSearchScreenRoute.route) }) {
            Text(text = "Nav to UserSearchScreen")
        }
    }
}
