package com.example.bsuir_repo_android.model.navigation

sealed class ScreenRoute(val route: String) {
    object ProfileScreenRoute : ScreenRoute("profile_screen")

    object ReposScreenRoute : ScreenRoute("repos_screen")

    object UserSearchScreenRoute : ScreenRoute("user_search_screen")

    object BeginScreenRoute : ScreenRoute("begin_screen_route")

    object LoginScreen : ScreenRoute("login_screen_route")

    object SignupScreen : ScreenRoute("signup_screen")
}
