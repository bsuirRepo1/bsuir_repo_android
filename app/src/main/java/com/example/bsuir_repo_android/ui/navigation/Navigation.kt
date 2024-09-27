package com.example.bsuir_repo_android.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bsuir_repo_android.model.navigation.ScreenRoute
import com.example.bsuir_repo_android.ui.app_screens.ProfileScreen
import com.example.bsuir_repo_android.ui.app_screens.RepositoriesScreen
import com.example.bsuir_repo_android.ui.app_screens.UserSearchScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(
    navController: NavHostController,
    scope: CoroutineScope,
    drawerState: DrawerState,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bsuir_repo_android_app") },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = null)
                    }
                },
            )
        },
    ) {
            innerPadding ->

        NavHost(navController = navController, startDestination = ScreenRoute.ProfileScreenRoute.route) {
            composable(route = ScreenRoute.ProfileScreenRoute.route) {
                ProfileScreen(
                    navController = navController,
                    modifier =
                        Modifier.padding(innerPadding)
                            .fillMaxSize(),
                )
            }
            composable(route = ScreenRoute.ReposScreenRoute.route) {
                RepositoriesScreen(
                    modifier =
                        Modifier.padding(innerPadding)
                            .fillMaxSize(),
                )
            }
            composable(route = ScreenRoute.UserSearchScreenRoute.route) {
                UserSearchScreen(
                    modifier =
                        Modifier.padding(innerPadding)
                            .fillMaxSize(),
                )
            }
        }
    }
}
