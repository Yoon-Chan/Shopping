package com.example.shopping.viewmodel.search

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.domain.model.Product
import com.example.domain.model.SearchKeyword
import com.example.domain.usecase.SearchUseCase
import com.example.shopping.delegate.ProductDelegate
import com.example.shopping.model.ProductVM
import com.example.shopping.ui.NavigationRouteName
import com.example.shopping.utils.NavigationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCase: SearchUseCase
) : ViewModel(), ProductDelegate {
    private val _searchResult = MutableStateFlow<List<ProductVM>>(emptyList())
    val searchResult: StateFlow<List<ProductVM>> = _searchResult.asStateFlow()
    val searchKeywords = useCase.getSearchKeywords()

    suspend fun search(keyword: String) {
        useCase.search(SearchKeyword(keyword)).collectLatest {
            _searchResult.emit(it.map(::convertToProductVM))
        }
    }

    private fun convertToProductVM(product: Product): ProductVM {
        return ProductVM(product, this)
    }


    override fun openProduct(navHostController: NavHostController, product: Product) {
        NavigationUtils.navigate(navHostController, NavigationRouteName.PRODUCT_DETAIL, product)
    }
}