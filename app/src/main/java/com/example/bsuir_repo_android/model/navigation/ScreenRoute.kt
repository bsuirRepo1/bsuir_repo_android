package com.example.bsuir_repo_android.model.navigation

sealed class ScreenRoute(val route: String) {
    object ProfileScreenRoute : ScreenRoute("profile_screen")

    object ReposScreenRoute : ScreenRoute("repos_screen")

    object UserSearchScreenRoute : ScreenRoute("user_search_screen")
}
