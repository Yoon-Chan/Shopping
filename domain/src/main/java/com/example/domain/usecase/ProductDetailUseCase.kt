package com.example.domain.usecase

import com.example.domain.model.Product
import com.example.domain.repository.ProductDetailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductDetailUseCase @Inject constructor(
    private val repository: ProductDetailRepository
) {
    fun getProductDetail(productId: String) : Flow<Product> {
        return repository.getProductDetail(productId)
    }

    suspend fun addBasket(product: Product) {
        repository.addBasket(product)
    }
}