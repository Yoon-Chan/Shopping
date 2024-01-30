package com.example.shopping.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.domain.model.Category
import com.example.shopping.ui.NavigationRouteName.DEEP_LINK_SCHEME
import com.example.shopping.ui.NavigationRouteName.MAIN_CATEGORY
import com.example.shopping.ui.NavigationRouteName.MAIN_HOME
import com.example.shopping.ui.NavigationRouteName.MAIN_LIKE
import com.example.shopping.ui.NavigationRouteName.MAIN_MY_PAGE
import com.example.shopping.utils.GsonUtils

sealed class MainNav(
    override val route: String,
    override val title: String,
    val icon: ImageVector
) :
    Destination {
    data object Home : MainNav(MAIN_HOME, NavigationTitle.MAIN_HOME, Icons.Default.Home) {
    }

    data object Category : MainNav(MAIN_CATEGORY,  NavigationTitle.MAIN_CATEGORY, Icons.Default.List)
    data object MyPage : MainNav(MAIN_MY_PAGE,  NavigationTitle.MAIN_MY_PAGE, Icons.Default.Settings)

    data object Like : MainNav(MAIN_LIKE,  NavigationTitle.MAIN_LIKE, Icons.Default.Favorite)
    companion object {
        fun isMainRoute(route: String?): Boolean {
            return when (route) {
                MAIN_HOME, MAIN_MY_PAGE, MAIN_CATEGORY, MAIN_LIKE -> true
                else -> false
            }
        }
    }

    override val deepLinks: List<NavDeepLink> = listOf(
        navDeepLink { uriPattern = "$DEEP_LINK_SCHEME$route" }
    )
}


object CategoryNav : DestinationArg<Category> {
    override val route: String
        get() = NavigationRouteName.CATEGORY

    override val argName: String
        get() = "category"
    override val arguments: List<NamedNavArgument> = listOf(
        navArgument(argName) { type = NavType.StringType }
    )
    override val deepLinks: List<NavDeepLink>
        get() = listOf(
            navDeepLink { uriPattern = "${DEEP_LINK_SCHEME}$route/{$argName}" }
        )
    override val title: String
        get() = NavigationTitle.CATEGORY

    override fun findArgument(navBackStackEntry: NavBackStackEntry): Category? {
        val categoryString = navBackStackEntry.arguments?.getString(argName)
        return GsonUtils.fromJson<Category>(categoryString)
    }

    override fun navigateWithArg(item: Category): String {
        val arg = GsonUtils.toJson(item)
        return "$route/$arg"
    }
}

object ProductDetailNav : DestinationArg<String> {
    override val route: String
        get() = NavigationRouteName.PRODUCT_DETAIL

    override val argName: String
        get() = "productId"
    override val arguments: List<NamedNavArgument> = listOf(
        navArgument(argName) { type = NavType.StringType }
    )
    override val deepLinks: List<NavDeepLink>
        get() = listOf(
            navDeepLink { uriPattern = "${DEEP_LINK_SCHEME}$route/{$argName}" }
        )
    override val title: String
        get() = NavigationTitle.PRODUCT_DETAIL

    override fun findArgument(navBackStackEntry: NavBackStackEntry): String? {
        val categoryString = navBackStackEntry.arguments?.getString(argName)
        return GsonUtils.fromJson<String>(categoryString)
    }

    override fun navigateWithArg(item: String): String {
        val arg = GsonUtils.toJson(item)
        return "$route/$arg"
    }
}

object SearchNav : Destination {
    override val route: String = NavigationRouteName.SEARCH
    override val title: String = NavigationTitle.SEARCH
    override val deepLinks: List<NavDeepLink> = listOf(
        navDeepLink { uriPattern = "${DEEP_LINK_SCHEME}$route" }
    )
}

object BasketNav : Destination {
    override val route: String = NavigationRouteName.BASKET
    override val title: String = NavigationTitle.BASKET
    override val deepLinks: List<NavDeepLink> = listOf(
        navDeepLink { uriPattern = "${DEEP_LINK_SCHEME}$route" }
    )
}

object PurchaseHistoryNav : Destination {
    override val route: String = NavigationRouteName.PURCHASE_HISTORY
    override val title: String = NavigationTitle.PURCHASE_HISTORY
    override val deepLinks: List<NavDeepLink> = listOf(
        navDeepLink { uriPattern = "${DEEP_LINK_SCHEME}$route" }
    )
}


interface Destination {
    val route: String
    val title: String
    val deepLinks: List<NavDeepLink>
}

interface DestinationArg<T> : Destination {
    val argName: String
    val arguments: List<NamedNavArgument>

    fun routeWithArgName() = "$route/{$argName}"
    fun navigateWithArg(item: T): String
    fun findArgument(navBackStackEntry: NavBackStackEntry): T?

}

object NavigationRouteName {
    const val DEEP_LINK_SCHEME = "shoppingapp://"
    const val MAIN_HOME = "main_home"
    const val MAIN_CATEGORY = "main_category"
    const val MAIN_MY_PAGE = "main_my_page"
    const val CATEGORY = "category"
    const val PRODUCT_DETAIL = "product_detail"
    const val SEARCH = "search"
    const val MAIN_LIKE = "main_like"
    const val BASKET = "basket"
    const val PURCHASE_HISTORY = "purchase_history"
}

object NavigationTitle {
    const val MAIN_HOME = "홈"
    const val MAIN_CATEGORY = "카테고리"
    const val MAIN_MY_PAGE = "마이페이지"
    const val CATEGORY = "카테고리별 보기"
    const val PRODUCT_DETAIL = "상품 상세 페이지"
    const val SEARCH = "검색"
    const val MAIN_LIKE = "좋아요 모아 보기"
    const val BASKET = "장바구니"
    const val PURCHASE_HISTORY = "결제 내역"
}