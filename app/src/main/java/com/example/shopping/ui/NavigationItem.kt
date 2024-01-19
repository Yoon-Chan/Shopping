package com.example.shopping.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.domain.model.Category
import com.example.domain.model.Product
import com.example.shopping.ui.NavigationRouteName.CATEGORY
import com.example.shopping.ui.NavigationRouteName.MAIN_CATEGORY
import com.example.shopping.ui.NavigationRouteName.MAIN_HOME
import com.example.shopping.ui.NavigationRouteName.MAIN_MY_PAGE
import com.example.shopping.ui.NavigationRouteName.PRODUCT_DETAIL

sealed class NavigationItem(open val route: String) {
    sealed class MainNav(override val route: String, val name: String, val icon: ImageVector) :
        NavigationItem(route) {
        data object Home : MainNav(MAIN_HOME, MAIN_HOME, Icons.Default.Home)
        data object Category : MainNav(MAIN_CATEGORY, MAIN_CATEGORY, Icons.Default.List)
        data object MyPage : MainNav(MAIN_MY_PAGE, MAIN_MY_PAGE, Icons.Default.Settings)

        companion object {
            fun isMainRoute(route: String?) : Boolean {
                return when(route) {
                    MAIN_HOME, MAIN_MY_PAGE, MAIN_CATEGORY -> true
                    else -> false
                }
            }
        }
    }

    data class CategoryNav(val category: Category) :
        NavigationItem(CATEGORY)

    data class ProductDetailNav(val product: Product) : NavigationItem(PRODUCT_DETAIL)
}

object NavigationRouteName {
    const val MAIN_HOME = "main_home"
    const val MAIN_CATEGORY = "main_category"
    const val MAIN_MY_PAGE = "main_my_page"
    const val CATEGORY = "category"
    const val PRODUCT_DETAIL = "product"
}