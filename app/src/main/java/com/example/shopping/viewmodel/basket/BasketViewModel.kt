package com.example.shopping.viewmodel.basket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Product
import com.example.domain.usecase.BasketUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor(
    private val basketUseCase: BasketUseCase
) : ViewModel() {
    val basketProducts = basketUseCase.getBasketProducts()

    fun removeBasketProduct(product: Product) {
        viewModelScope.launch {
            basketUseCase.removeBasketProduct(product)
        }
    }
}