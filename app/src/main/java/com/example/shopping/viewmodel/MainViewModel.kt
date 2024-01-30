package com.example.shopping.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.domain.model.AccountInfo
import com.example.domain.model.Banner
import com.example.domain.model.BannerList
import com.example.domain.model.BaseModel
import com.example.domain.model.Carousel
import com.example.domain.model.Category
import com.example.domain.model.Product
import com.example.domain.model.Ranking
import com.example.domain.usecase.AccountUseCase
import com.example.domain.usecase.CategoryUseCase
import com.example.domain.usecase.LikeUseCase
import com.example.domain.usecase.MainUseCase
import com.example.shopping.delegate.BannerDelegate
import com.example.shopping.delegate.CategoryDelegate
import com.example.shopping.delegate.ProductDelegate
import com.example.shopping.model.BannerListVM
import com.example.shopping.model.BannerVM
import com.example.shopping.model.CarouselVM
import com.example.shopping.model.PresentationVM
import com.example.shopping.model.ProductVM
import com.example.shopping.model.RankingVM
import com.example.shopping.ui.BasketNav
import com.example.shopping.ui.CategoryNav
import com.example.shopping.ui.ProductDetailNav
import com.example.shopping.ui.SearchNav
import com.example.shopping.utils.NavigationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainUseCase: MainUseCase,
    categoryUseCase: CategoryUseCase,
    private val accountUseCase: AccountUseCase,
    private val likeUseCase: LikeUseCase
) : ViewModel(), ProductDelegate, BannerDelegate, CategoryDelegate {

    private val _columnCount = MutableStateFlow(DEFAULT_COLUMN_COUNT)
    val columnCount = _columnCount.asStateFlow()

    val modelList = mainUseCase.getModelList().map(::convertToPresentationVM)
    val categoryList = categoryUseCase.getCategories()
    val accountInfo = accountUseCase.getAccountInfo()
    val likeProduct = likeUseCase.getLikeProduct().map(::convertToPresentationVM)

    fun signIn(accountInfo: AccountInfo){
        viewModelScope.launch {
            accountUseCase.signInGoogle(accountInfo)
        }
    }

    fun signOut() {
        viewModelScope.launch {
            accountUseCase.signOut()
        }
    }

    fun openBasket(navHostController: NavHostController) {
        NavigationUtils.navigate(navHostController, BasketNav.route)
    }

    fun openSearchForm(navHostController: NavHostController) {
        NavigationUtils.navigate(navHostController,SearchNav.route)
    }

    fun updateColumnCount(count: Int) {
        viewModelScope.launch {
            _columnCount.emit(count)
        }
    }

    override fun openProduct(navHostController: NavHostController,product: Product) {
        NavigationUtils.navigate(navHostController, ProductDetailNav.navigateWithArg(product.productId))
    }

    override fun openBanner(bannerId: String) {
        TODO("Not yet implemented")
    }

    override fun openCategory(navHostController: NavHostController, category: Category) {
        NavigationUtils.navigate(navHostController, CategoryNav.navigateWithArg(category))
    }

    private fun convertToPresentationVM(list: List<BaseModel>): List<PresentationVM<out BaseModel>> {
        return list.map { model ->
            when (model) {
                is Product -> ProductVM(model, this)
                is Ranking -> RankingVM(model, this)
                is Carousel -> CarouselVM(model, this)
                is Banner -> BannerVM(model, this)
                is BannerList -> BannerListVM(model, this)
            }
        }
    }

    override fun likeProduct(product: Product) {
        viewModelScope.launch {
            mainUseCase.likeProduct(product)
        }
    }

    companion object {
        private const val DEFAULT_COLUMN_COUNT = 2
    }
}