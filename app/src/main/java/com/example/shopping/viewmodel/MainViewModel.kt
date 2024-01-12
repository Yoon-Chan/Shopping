package com.example.shopping.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Banner
import com.example.domain.model.BannerList
import com.example.domain.model.Product
import com.example.domain.model.Ranking
import com.example.domain.usecase.MainUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    useCase: MainUseCase
) : ViewModel() {

    private val _columnCount = MutableStateFlow(DEFAULT_COLUMN_COUNT)
    val columnCount = _columnCount.asStateFlow()

    val modelList = useCase.getModelList()

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

    companion object {
        private const val DEFAULT_COLUMN_COUNT = 2
    }
}