package com.example.shopping.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.shopping.ui.basket.BasketScreen
import com.example.shopping.ui.category.CategoryScreen
import com.example.shopping.ui.main.LikeScreen
import com.example.shopping.ui.main.MainCategoryScreen
import com.example.shopping.ui.main.MainHomeInsideScreen
import com.example.shopping.ui.main.MyPageScreen
import com.example.shopping.ui.product_detail.ProductDetailScreen
import com.example.shopping.ui.search.SearchScreen
import com.example.shopping.utils.NavigationUtils
import com.example.shopping.viewmodel.MainViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInClient

//sealed class MainNavigationItem(val route: String, val name: String, val icon: ImageVector) {
//    data object Main : MainNavigationItem("MainNav", "MainNav", Icons.Default.Home)
//    data object Category : MainNavigationItem("Category", "Category", Icons.Default.List)
//    data object MyPage : MainNavigationItem("MyPage", "MyPage", Icons.Default.Settings)
//}


//@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
//@Composable
//fun DefaultPreview() {
//    ShoppingTheme {
//        HomeScreen()
//    }
//}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(googleSignInClient: GoogleSignInClient, viewModel: MainViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        topBar = {
            MainHeader(navController, viewModel, currentRoute)
        },
        bottomBar = {
            if (MainNav.isMainRoute(currentRoute)) {
                MainBottomNavigationBar(navController, currentRoute)
            }

        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            MainNavigationScreen(viewModel, navController, googleSignInClient)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainHeader(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    currentRoute: String?
) {
    TopAppBar(
        title = { Text(text = NavigationUtils.findDestination(currentRoute).title) },
        navigationIcon = {
            if (!MainNav.isMainRoute(currentRoute)) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "back")
                }
            } else null
        },
        actions = {
            if (MainNav.isMainRoute(currentRoute)) {
                IconButton(onClick = {
                    mainViewModel.openSearchForm(navController)
                }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "SearchIcon")
                }
                IconButton(onClick = {
                    mainViewModel.openBasket(navController)
                }) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "BasketIcon"
                    )
                }
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
fun MainBottomNavigationBar(navController: NavHostController, currentRoute: String?) {
    val bottomNavigationItems = listOf(
        MainNav.Home,
        MainNav.Category,
        MainNav.Like,
        MainNav.MyPage,
    )

    NavigationBar {
        bottomNavigationItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    NavigationUtils.navigate(
                        navController,
                        item.route,
                        navController.graph.startDestinationRoute
                    )
                },
                icon = {
                    Icon(imageVector = item.icon, contentDescription = item.title)
                },
                label = { Text(text = item.title) })
        }
    }
}


@Composable
fun MainNavigationScreen(
    viewModel: MainViewModel,
    navController: NavHostController,
    googleSignInClient: GoogleSignInClient
) {
    NavHost(navController = navController, startDestination = MainNav.Home.route) {
        composable(MainNav.Home.route, deepLinks = MainNav.Home.deepLinks) {
            MainHomeInsideScreen(navController, viewModel)
        }
        composable(MainNav.Category.route, deepLinks = MainNav.Category.deepLinks) {
            MainCategoryScreen(viewModel = viewModel, navController = navController)
        }
        composable(MainNav.MyPage.route,deepLinks = MainNav.MyPage.deepLinks) {
            MyPageScreen(viewModel = viewModel, googleSignInClient = googleSignInClient)
        }
        composable(MainNav.Like.route, deepLinks = MainNav.Like.deepLinks) {
            LikeScreen(navHostController = navController, viewModel = viewModel)
        }

        composable(
            CategoryNav.routeWithArgName(),
            arguments = CategoryNav.arguments,
            deepLinks = CategoryNav.deepLinks
        ) {
            val categoryString = it.arguments?.getString("category")
            val category = CategoryNav.findArgument(it)
            if (category != null) {
                CategoryScreen(navHostController = navController, category = category)
            }
        }
        composable(
            route = ProductDetailNav.routeWithArgName(),
            arguments = ProductDetailNav.arguments,
            deepLinks = ProductDetailNav.deepLinks
        ) {
            val productString = ProductDetailNav.findArgument(it)
            if (productString != null) {
                ProductDetailScreen(productString)
            }
        }
        composable(
            SearchNav.route,
            deepLinks = SearchNav.deepLinks
        ) {
            SearchScreen(navHostController = navController)
        }
        composable(BasketNav.route, deepLinks = BasketNav.deepLinks) {
            BasketScreen()
        }
    }

}
