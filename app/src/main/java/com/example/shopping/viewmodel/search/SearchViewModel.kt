package com.example.shopping.viewmodel.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.domain.model.Product
import com.example.domain.model.SearchFilter
import com.example.domain.model.SearchKeyword
import com.example.domain.usecase.SearchUseCase
import com.example.shopping.delegate.ProductDelegate
import com.example.shopping.model.ProductVM
import com.example.shopping.ui.ProductDetailNav
import com.example.shopping.utils.NavigationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCase: SearchUseCase
) : ViewModel(), ProductDelegate {

    private val searchManager = SearchManager()
    private val _searchResult = MutableStateFlow<List<ProductVM>>(emptyList())
    val searchResult: StateFlow<List<ProductVM>> = _searchResult.asStateFlow()
    val searchKeywords = useCase.getSearchKeywords()
    val searchFilters = searchManager.filters

    fun search(keyword: String) {
        viewModelScope.launch {
            searchInternalNewSearchKeyword(keyword)
        }
    }

    fun updateFilter(filter: SearchFilter) {
        viewModelScope.launch {
            searchManager.updateFilter(filter)
            searchInternal()
        }
    }

    private suspend fun searchInternal() {
        useCase.search(searchManager.searchKeyword, searchManager.currentFilters()).collectLatest {
            _searchResult.emit(it.map(::convertToProductVM))
        }
    }

    private suspend fun searchInternalNewSearchKeyword(newSearchKeyword: String = "") {
        searchManager.clearFilter()

        useCase.search(SearchKeyword(newSearchKeyword), searchManager.currentFilters())
            .collectLatest {
                searchManager.initSearchManager(newSearchKeyword, it)
                _searchResult.emit(it.map(::convertToProductVM))
            }
    }

    private fun convertToProductVM(product: Product): ProductVM {
        return ProductVM(product, this)
    }


    override fun openProduct(navHostController: NavHostController, product: Product) {
        NavigationUtils.navigate(navHostController, ProductDetailNav.navigateWithArg(product.productId))
    }

    override fun likeProduct(product: Product) {
        viewModelScope.launch {
            useCase.likeProduct(product = product)
        }
    }
}