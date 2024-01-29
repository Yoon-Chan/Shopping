package com.example.domain.usecase

import com.example.domain.model.BasketProduct
import com.example.domain.model.Product
import com.example.domain.repository.BasketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BasketUseCase @Inject constructor(
    private val repository: BasketRepository
) {
    fun getBasketProducts(): Flow<List<BasketProduct>> {
        return repository.getBasketProducts()
    }

    suspend fun removeBasketProduct(product: Product) {
        return repository.removeBasketProduct(product)
    }
}