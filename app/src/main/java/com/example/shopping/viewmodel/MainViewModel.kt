package com.example.shopping.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.domain.model.Banner
import com.example.domain.model.BannerList
import com.example.domain.model.Category
import com.example.domain.model.Product
import com.example.domain.usecase.CategoryUseCase
import com.example.domain.usecase.MainUseCase
import com.example.shopping.ui.NavigationRouteName
import com.example.shopping.utils.NavigationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    mainUseCase: MainUseCase,
    categoryUseCase: CategoryUseCase,
) : ViewModel() {

    private val _columnCount = MutableStateFlow(DEFAULT_COLUMN_COUNT)
    val columnCount = _columnCount.asStateFlow()

    val modelList = mainUseCase.getModelList()
    val categoryList = categoryUseCase.getCategories()

    fun openSearchForm() {
        Log.e("MainViewModel", "click openSearchForm")
    }

    fun updateColumnCount(count: Int){
        viewModelScope.launch {
            _columnCount.emit(count)
        }
    }

    fun openProduct(product: Product){

    }

    fun openCarousel(product: Product){

    }

    fun openBanner(banner: Banner){

    }

    fun openBannerList(banner: BannerList){

    }

    fun openRankingProduct(ranking: Product){

    }

    fun openCategory(navHostController: NavHostController, category: Category){
        NavigationUtils.navigate(navHostController, NavigationRouteName.CATEGORY, category)
    }

    companion object {
        private const val DEFAULT_COLUMN_COUNT = 2
    }
}