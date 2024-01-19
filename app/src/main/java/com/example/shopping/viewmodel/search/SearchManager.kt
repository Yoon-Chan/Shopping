package com.example.shopping.viewmodel.search

import com.example.domain.model.Category
import com.example.domain.model.Product
import com.example.domain.model.SearchFilter
import com.example.domain.model.SearchKeyword
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.math.max
import kotlin.math.min

class SearchManager {
    private val _filters = MutableStateFlow<List<SearchFilter>>(listOf())
    val filters: StateFlow<List<SearchFilter>> = _filters.asStateFlow()

    var searchKeyword = SearchKeyword("")
        private set

    suspend fun initSearchManager(newSearchKeyword: String, searchResult: List<Product>) {
        val categories = mutableListOf<Category>()
        var minPrice = Int.MAX_VALUE
        var maxPrice = Int.MIN_VALUE

        searchResult.forEach {
            if (!categories.contains(it.category)) {
                categories.add(it.category)
            }
            minPrice = min(minPrice, it.price.finalPrice)
            maxPrice = max(maxPrice, it.price.finalPrice)
        }
        _filters.emit(
            listOf(
                SearchFilter.PriceFilter(minPrice to maxPrice),
                SearchFilter.CategoryFilter(categories)
            )
        )
        searchKeyword = SearchKeyword(newSearchKeyword)
    }

    suspend fun updateFilter(filter: SearchFilter) {
        val currentFilter = filters.value

        _filters.emit(currentFilter.map {
            if(it is SearchFilter.PriceFilter && filter is SearchFilter.PriceFilter){
                it.selectedRange = filter.selectedRange
            }
            if(it is SearchFilter.CategoryFilter && filter is SearchFilter.CategoryFilter){
                it.selectedCategory = filter.selectedCategory
            }
            it
        })
    }

    fun currentFilters(): List<SearchFilter> = filters.value
}