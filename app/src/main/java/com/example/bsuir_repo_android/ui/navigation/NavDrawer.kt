package com.example.bsuir_repo_android.ui.navigation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.bsuir_repo_android.model.navigation.DrawerNavItem
import com.example.bsuir_repo_android.model.navigation.ScreenRoute
import kotlinx.coroutines.launch

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
                                navController.navigate(ScreenRoute.ProfileScreenRoute.route)
                            } else if (item.title == "Repos") {
                                navController.navigate(ScreenRoute.ReposScreenRoute.route)
                            } else if (item.title == "UserSearch") {
                                navController.navigate(ScreenRoute.UserSearchScreenRoute.route)
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
        Navigation(navController = navController, scope = scope, drawerState = drawerState)
    }
}
