package com.example.shopping.utils

import androidx.navigation.NavHostController
import com.example.shopping.ui.BasketNav
import com.example.shopping.ui.CategoryNav
import com.example.shopping.ui.Destination
import com.example.shopping.ui.MainNav
import com.example.shopping.ui.NavigationRouteName
import com.example.shopping.ui.ProductDetailNav
import com.example.shopping.ui.PurchaseHistoryNav
import com.example.shopping.ui.SearchNav

object NavigationUtils {
    fun navigate(
        controller: NavHostController,
        routeName: String,
        backStackRouteName: String? = null,
        isLaunchSingleTop: Boolean = true,
        needToRestoreState: Boolean = true,
    ) {
        controller.navigate(routeName) {
            if(backStackRouteName != null){
                popUpTo(backStackRouteName) {
                    saveState = true
                }
                launchSingleTop = isLaunchSingleTop
                restoreState = needToRestoreState
            }
        }
    }

    fun findDestination(route: String?) :Destination {
        return when(route) {
            NavigationRouteName.MAIN_HOME -> MainNav.Home
            NavigationRouteName.MAIN_LIKE -> MainNav.Like
            NavigationRouteName.MAIN_CATEGORY -> MainNav.Category
            NavigationRouteName.MAIN_MY_PAGE -> MainNav.MyPage
            NavigationRouteName.SEARCH -> SearchNav
            NavigationRouteName.BASKET -> BasketNav
            NavigationRouteName.PURCHASE_HISTORY -> PurchaseHistoryNav


            ProductDetailNav.routeWithArgName() -> CategoryNav
            CategoryNav.routeWithArgName() -> ProductDetailNav
            else -> MainNav.Home
        }
    }
}