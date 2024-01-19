package com.example.domain.usecase

import com.example.domain.model.Category
import com.example.domain.model.Product
import com.example.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    fun getCategories(): Flow<List<Category>> {
        return categoryRepository.getCategories()
    }

    fun getProductByCategory(category: Category) : Flow<List<Product>> {
        return categoryRepository.getProductsByCategory(category = category)
    }
}