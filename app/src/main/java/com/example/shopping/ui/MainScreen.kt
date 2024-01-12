package com.example.shopping.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.shopping.ui.main.MainInsideScreen
import com.example.shopping.viewmodel.MainViewModel
import com.example.shopping.ui.theme.ShoppingTheme

sealed class MainNavigationItem(val route: String, val name: String, val icon: ImageVector) {
    data object Main : MainNavigationItem("Main", "Main", Icons.Default.Home)
    data object Category : MainNavigationItem("Category", "Category", Icons.Default.List)
    data object MyPage : MainNavigationItem("MyPage", "MyPage", Icons.Default.Settings)
}


@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun DefaultPreview() {
    ShoppingTheme {
        MainScreen()
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            Header(viewModel)

        },
        bottomBar = {
            MainBottomNavigationBar(navController)

        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            MainNavigationScreen(viewModel, navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(mainViewModel: MainViewModel) {
    TopAppBar(
        title = { Text(text = "My App") },
        actions = {
            IconButton(onClick = {
                mainViewModel.openSearchForm()
            }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "SearchIcon")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Composable
fun MainBottomNavigationBar(navController: NavHostController) {
    val bottomNavigationItems = listOf(
        MainNavigationItem.Main,
        MainNavigationItem.Category,
        MainNavigationItem.MyPage,
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        bottomNavigationItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(imageVector = item.icon, contentDescription = item.name)
                },
                label = { Text(text = item.name) })
        }
    }
}


@Composable
fun MainNavigationScreen(viewModel: MainViewModel, navController: NavHostController) {
    NavHost(navController = navController, startDestination = MainNavigationItem.Main.route) {
        composable(MainNavigationItem.Main.route) {
            MainInsideScreen(viewModel)
        }
        composable(MainNavigationItem.Category.route) {
            Text(text = "Hello Category")
        }
        composable(MainNavigationItem.MyPage.route) {
            Text(text = "Hello Settings")
        }
    }

}
