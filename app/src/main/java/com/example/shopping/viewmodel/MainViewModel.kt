package com.example.shopping.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.domain.model.Banner
import com.example.domain.model.BannerList
import com.example.domain.model.BaseModel
import com.example.domain.model.Carousel
import com.example.domain.model.Category
import com.example.domain.model.Product
import com.example.domain.model.Ranking
import com.example.domain.usecase.CategoryUseCase
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
import com.example.shopping.ui.NavigationRouteName
import com.example.shopping.utils.NavigationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    mainUseCase: MainUseCase,
    categoryUseCase: CategoryUseCase,
) : ViewModel(), ProductDelegate, BannerDelegate, CategoryDelegate {

    private val _columnCount = MutableStateFlow(DEFAULT_COLUMN_COUNT)
    val columnCount = _columnCount.asStateFlow()

    val modelList = mainUseCase.getModelList().map(::convertToPresentationVM)
    val categoryList = categoryUseCase.getCategories()

    fun openSearchForm() {
        Log.e("MainViewModel", "click openSearchForm")
    }

    fun updateColumnCount(count: Int) {
        viewModelScope.launch {
            _columnCount.emit(count)
        }
    }

    override fun openProduct(product: Product) {

    }

    override fun openBanner(bannerId: String) {
        TODO("Not yet implemented")
    }

    override fun openCategory(navHostController: NavHostController, category: Category) {
        NavigationUtils.navigate(navHostController, NavigationRouteName.CATEGORY, category)
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

    companion object {
        private const val DEFAULT_COLUMN_COUNT = 2
    }
}