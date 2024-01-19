package com.example.domain.usecase

import com.example.domain.model.Product
import com.example.domain.model.SearchKeyword
import com.example.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val repository: SearchRepository
) {

    suspend fun search(keyword: SearchKeyword): Flow<List<Product>> {
        return repository.search(keyword)
    }

    fun getSearchKeywords(): Flow<List<SearchKeyword>> {
        return repository.getSearchKeywords()
    }
}