package com.example.bsuir_repo_android.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import com.example.bsuir_repo_android.R
import com.example.bsuir_repo_android.ui.app_screens.MainScreen
import com.example.bsuir_repo_android.ui.app_screens.ProfileScreen
import com.example.bsuir_repo_android.ui.app_screens.RepositoriesScreen
import com.example.bsuir_repo_android.ui.login.BeginScreen
import com.example.bsuir_repo_android.ui.login.LoginScreen
import com.example.bsuir_repo_android.ui.login.SignupScreen

private object Routes {
    // First Graph Route
    const val AUTH = "auth"

    const val BEGIN = "begin"
    const val REGISTER = "signup"
    const val LOGIN = "login"
    const val RESTORE_PASSWORD = "restore_password"

    // Second Graph Route
    const val MAIN = "main"

    const val MAIN_PAGE = "home"
    const val PROFILE = "profile"
    const val REPOS = "repos"
    const val NOTIFICATION = "notification"
}

sealed class TopLevelDestination(
    val route: String,
    val title: Int? = null,
    val selectedIcon: ImageVector? = null,
    val unselectedIcon: ImageVector? = null,
)

sealed class ScreenRoute(val route: String) {
    object Auth : ScreenRoute(Routes.AUTH) {
        object LoginScreen : ScreenRoute(Routes.LOGIN)

        object SignupScreen : ScreenRoute(Routes.REGISTER)

        object BeginScreen : ScreenRoute(Routes.BEGIN)

        object RestorePasswordScreen : ScreenRoute(Routes.RESTORE_PASSWORD)
    }

    object Main : TopLevelDestination(Routes.MAIN) {
        object ProfileScreen : TopLevelDestination(
            route = Routes.PROFILE,
            title = R.string.profile,
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person,
        )

        object ReposScreen : TopLevelDestination(
            route = Routes.REPOS,
            title = R.string.repos,
            selectedIcon = Icons.Filled.List,
            unselectedIcon = Icons.Outlined.List,
        )

        object MainScreen : TopLevelDestination(
            route = Routes.MAIN_PAGE,
            title = R.string.main,
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
        )
    }
}

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        route = ScreenRoute.Auth.route,
        startDestination = ScreenRoute.Auth.BeginScreen.route,
    ) {
        composable(
            route = ScreenRoute.Auth.BeginScreen.route,
        ) {
            BeginScreen(navController = navController)
        }

        composable(
            route = ScreenRoute.Auth.SignupScreen.route,
        ) {
            SignupScreen(navController = navController)
        }

        composable(
            route = ScreenRoute.Auth.LoginScreen.route,
        ) {
            LoginScreen(navController = navController)
        }

        composable(
            route = ScreenRoute.Auth.RestorePasswordScreen.route,
        ) {
        }
    }
}

fun NavGraphBuilder.mainNavGraph(navController: NavHostController) {
    navigation(
        route = ScreenRoute.Main.route,
        startDestination = ScreenRoute.Main.MainScreen.route,
    ) {
        composable(
            route = ScreenRoute.Main.MainScreen.route,
        ) {
            MainScreen(modifier = Modifier)
        }

        composable(
            route = ScreenRoute.Main.ProfileScreen.route,
        ) {
            ProfileScreen(navController = navController, modifier = Modifier)
        }

        composable(
            route = ScreenRoute.Main.ReposScreen.route,
        ) {
            RepositoriesScreen(modifier = Modifier)
        }
    }
}

@Composable
fun RootNavGraph(
    isLoggedIn: Boolean,
    navHostController: NavHostController,
) {
    NavHost(
        navController = navHostController,
        startDestination = if (isLoggedIn) ScreenRoute.Main.route else ScreenRoute.Auth.route,
    ) {
        authNavGraph(navHostController)
        mainNavGraph(navHostController)
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val navigationScreen =
        listOf(
            ScreenRoute.Main.ReposScreen,
            ScreenRoute.Main.MainScreen,
            ScreenRoute.Main.ProfileScreen,
        )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        navigationScreen.forEach { item ->
            NavigationBarItem(
                selected =
                    currentRoute == item.route,
                label = {
                    Text(text = stringResource(id = item.title!!))
                },
                icon = @Composable {
                    Icon(
                        imageVector = (
                            if (item.route == currentRoute) {
                                item.selectedIcon!!
                            } else {
                                item.unselectedIcon!!
                            }
                        ),
                        contentDescription = stringResource(id = item.title!!),
                    )
                },
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    }
}
