package com.example.bsuir_repo_android.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class DrawerNavItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)

object DrawerNavTitles {
    const val MAIN = "Main"
    const val PROFILE = "Profile"
    const val REPOS = "Repositories"
}
/*

@Composable
fun NavDrawer() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }

    val items =
        listOf(
            DrawerNavItem(
                title = "Profile",
                selectedIcon = Icons.Filled.Person,
                unselectedIcon = Icons.Outlined.Person,
            ),
            DrawerNavItem(
                title = "Repos",
                selectedIcon = Icons.Filled.Lock,
                unselectedIcon = Icons.Outlined.Lock,
            ),
            DrawerNavItem(
                title = "UserSearch",
                selectedIcon = Icons.Filled.Search,
                unselectedIcon = Icons.Outlined.Search,
            ),
        )

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(16.dp))
                items.forEachIndexed {
                        index, item ->
                    NavigationDrawerItem(
                        label = { Text(text = item.title) },
                        selected = index == selectedItemIndex,
                        onClick = {
                            if (item.title == "Profile") {
                                navController.navigate(ScreenRoute.Main.ProfileScreen.route)
                            } else if (item.title == "Repos") {
                                navController.navigate(ScreenRoute.Main.ReposScreen.route)
                            } else if (item.title == "MainSearch") {
                                navController.navigate(ScreenRoute.Main.MainScreen.route)
                            }
                            selectedItemIndex = index
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        icon = {
                            Icon(
                                imageVector =
                                if (index == selectedItemIndex) {
                                    item.selectedIcon
                                } else {
                                    item.unselectedIcon
                                },
                                contentDescription = null,
                            )
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                    )
                }
            }
        },
        drawerState = drawerState,
    ) {
        // Navigation(navController = navController, scope = scope, drawerState = drawerState)
    }
}*/
