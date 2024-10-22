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
import com.example.bsuir_repo_android.ui.login.BeginScreen
import com.example.bsuir_repo_android.ui.login.LoginScreen
import com.example.bsuir_repo_android.ui.login.SignupScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(
    navController: NavHostController,
    scope: CoroutineScope,
    drawerState: DrawerState,
) {
    NavHost(navController = navController, startDestination = ScreenRoute.BeginScreenRoute.route) {
        composable(route = ScreenRoute.ProfileScreenRoute.route) {
            AppScaffold(
                navController = navController,
                scope = scope,
                drawerState = drawerState,
                route = ScreenRoute.ProfileScreenRoute.route,
            )
        }
        composable(route = ScreenRoute.ReposScreenRoute.route) {
            AppScaffold(navController = navController, scope = scope, drawerState = drawerState, route = ScreenRoute.ReposScreenRoute.route)
        }
        composable(route = ScreenRoute.UserSearchScreenRoute.route) {
            AppScaffold(
                navController = navController,
                scope = scope,
                drawerState = drawerState,
                route = ScreenRoute.UserSearchScreenRoute.route,
            )
        }
        composable(route = ScreenRoute.BeginScreenRoute.route) {
            BeginScreen(navController = navController)
        }
        composable(route = ScreenRoute.SignupScreen.route) {
            SignupScreen(navController = navController)
        }
        composable(route = ScreenRoute.LoginScreen.route) {
            LoginScreen(navController = navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    navController: NavHostController,
    scope: CoroutineScope,
    drawerState: DrawerState,
    route: String,
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
        if (route == ScreenRoute.ProfileScreenRoute.route) {
            ProfileScreen(
                navController = navController,
                modifier =
                    Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
            )
        } else if (route == ScreenRoute.UserSearchScreenRoute.route) {
            UserSearchScreen(
                modifier =
                    Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
            )
        } else if (route == ScreenRoute.ReposScreenRoute.route) {
            RepositoriesScreen(
                modifier =
                    Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
            )
        }
    }
}
