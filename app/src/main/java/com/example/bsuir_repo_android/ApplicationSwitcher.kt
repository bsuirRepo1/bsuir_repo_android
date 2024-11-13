package com.example.bsuir_repo_android

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.bsuir_repo_android.ui.navigation.BottomBar
import com.example.bsuir_repo_android.ui.navigation.DrawerNavItem
import com.example.bsuir_repo_android.ui.navigation.DrawerNavTitles
import com.example.bsuir_repo_android.ui.navigation.RootNavGraph
import com.example.bsuir_repo_android.ui.navigation.ScreenRoute
import com.example.bsuir_repo_android.viewModel.AuthState
import kotlinx.coroutines.launch

@Composable
fun ApplicationSwitcher(navController: NavHostController) {
    val authViewModel = AuthState.current
    val isLoggedIn = authViewModel.apiState.collectAsState().value.isSuccess

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val snackbarHostState = remember { SnackbarHostState() }

    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }
    val topBarState = rememberSaveable { (mutableStateOf(true)) }

    val navDrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }

    val items =
        listOf(
            DrawerNavItem(
                title = DrawerNavTitles.PROFILE,
                selectedIcon = Icons.Filled.Person,
                unselectedIcon = Icons.Outlined.Person,
            ),
            DrawerNavItem(
                title = DrawerNavTitles.MAIN,
                selectedIcon = Icons.Filled.List,
                unselectedIcon = Icons.Outlined.List,
            ),
            DrawerNavItem(
                title = DrawerNavTitles.REPOS,
                selectedIcon = Icons.Filled.Lock,
                unselectedIcon = Icons.Outlined.Lock,
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
                            if (item.title == DrawerNavTitles.PROFILE) {
                                navController.navigate(ScreenRoute.Main.ProfileScreen.route)
                            } else if (item.title == DrawerNavTitles.REPOS) {
                                navController.navigate(ScreenRoute.Main.ReposScreen.route)
                            } else if (item.title == DrawerNavTitles.MAIN) {
                                navController.navigate(ScreenRoute.Main.MainScreen.route)
                            }
                            selectedItemIndex = index
                            scope.launch {
                                navDrawerState.close()
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
        drawerState = navDrawerState,
    ) {
        when (navBackStackEntry?.destination?.route) {
            ScreenRoute.Main.MainScreen.route -> {
                bottomBarState.value = true
                topBarState.value = true
            }

            ScreenRoute.Main.ProfileScreen.route -> {
                bottomBarState.value = true
                topBarState.value = true
            }

            ScreenRoute.Main.ReposScreen.route -> {
                bottomBarState.value = true
                topBarState.value = true
            }

            else -> {
                bottomBarState.value = false
                topBarState.value = false
            }
        }

        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
            bottomBar = {
                if (bottomBarState.value) {
                    BottomBar(navController = navController)
                }
            },
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                RootNavGraph(isLoggedIn = isLoggedIn, navHostController = navController)
            }
        }
    }
}
