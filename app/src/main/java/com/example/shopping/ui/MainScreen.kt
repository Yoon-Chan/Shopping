package com.example.shopping.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.domain.model.Category
import com.example.shopping.ui.basket.BasketScreen
import com.example.shopping.ui.category.CategoryScreen
import com.example.shopping.ui.main.LikeScreen
import com.example.shopping.ui.main.MainCategoryScreen
import com.example.shopping.ui.main.MainHomeInsideScreen
import com.example.shopping.ui.main.MyPageScreen
import com.example.shopping.ui.product_detail.ProductDetailScreen
import com.example.shopping.ui.search.SearchScreen
import com.example.shopping.viewmodel.MainViewModel
import com.example.shopping.ui.theme.ShoppingTheme
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.gson.Gson

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
fun HomeScreen(googleSignInClient: GoogleSignInClient,viewModel: MainViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        topBar = {
            if(NavigationItem.MainNav.isMainRoute(currentRoute)){
                MainHeader(navController, viewModel)
            }

        },
        bottomBar = {
            if (NavigationItem.MainNav.isMainRoute(currentRoute)) {
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
fun MainHeader(navController: NavHostController, mainViewModel: MainViewModel) {
    TopAppBar(
        title = { Text(text = "My App") },
        actions = {
            IconButton(onClick = {
                mainViewModel.openSearchForm(navController)
            }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "SearchIcon")
            }
            IconButton(onClick = {
                mainViewModel.openBasket(navController)
            }) {
                Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "BasketIcon")
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
        NavigationItem.MainNav.Home,
        NavigationItem.MainNav.Category,
        NavigationItem.MainNav.LIKE,
        NavigationItem.MainNav.MyPage,
    )

    NavigationBar {
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
fun MainNavigationScreen(viewModel: MainViewModel, navController: NavHostController, googleSignInClient: GoogleSignInClient) {
    NavHost(navController = navController, startDestination = NavigationRouteName.MAIN_HOME) {
        composable(NavigationRouteName.MAIN_HOME) {
            MainHomeInsideScreen(navController, viewModel)
        }
        composable(NavigationRouteName.MAIN_CATEGORY) {
            MainCategoryScreen(viewModel = viewModel, navController = navController)
        }
        composable(NavigationRouteName.MAIN_MY_PAGE) {
            MyPageScreen(viewModel = viewModel, googleSignInClient = googleSignInClient)
        }
        composable(NavigationRouteName.MAIN_LIKE) {
            LikeScreen(navHostController = navController, viewModel = viewModel)
        }
        composable(
            NavigationRouteName.CATEGORY + "/{category}",
            arguments = listOf(navArgument("category") { type = NavType.StringType })
        ) {
            val categoryString = it.arguments?.getString("category")
            val category = Gson().fromJson(categoryString, Category::class.java)
            if (category != null) {
                CategoryScreen(navHostController = navController, category = category)
            }
        }
        composable(
            NavigationRouteName.PRODUCT_DETAIL + "/{product}",
            arguments = listOf(navArgument("product") { type = NavType.StringType })
        ) {
            val productString = it.arguments?.getString("product")
            if (productString != null) {
                ProductDetailScreen(productString)
            }
        }
        composable(
            NavigationRouteName.SEARCH
        ){
            SearchScreen(navHostController = navController)
        }
        composable(NavigationRouteName.BASKET) {
            BasketScreen()
        }
    }

}
