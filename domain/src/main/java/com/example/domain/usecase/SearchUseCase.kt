package com.example.domain.usecase

import com.example.domain.model.Product
import com.example.domain.model.SearchFilter
import com.example.domain.model.SearchKeyword
import com.example.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val repository: SearchRepository
) {

    suspend fun search(keyword: SearchKeyword, filters: List<SearchFilter>): Flow<List<Product>> {
        return repository.search(keyword, filters)
    }

    fun getSearchKeywords(): Flow<List<SearchKeyword>> {
        return repository.getSearchKeywords()
    }

    suspend fun likeProduct(product: Product){
        repository.likeProduct(product)
    }
}