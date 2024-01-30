package com.example.shopping.viewmodel.basket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.BasketProduct
import com.example.domain.model.Product
import com.example.domain.usecase.BasketUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor(
    private val basketUseCase: BasketUseCase
) : ViewModel() {
    val basketProducts = basketUseCase.getBasketProducts()
    private val _eventFlow = MutableSharedFlow<BasketEvent>()
    val eventFLow: SharedFlow<BasketEvent> = _eventFlow

    fun dispatch(action: BasketAction) {
        when (action) {
            is BasketAction.RemoveProduct -> {
                removeBasketProduct(action.product)
            }

            is BasketAction.CheckoutBasket -> {
                checkoutBasket(action.products)
            }
        }
    }

    fun removeBasketProduct(product: Product) {
        viewModelScope.launch {
            basketUseCase.removeBasketProduct(product)
        }
    }

    fun checkoutBasket(products: List<BasketProduct>) {
        viewModelScope.launch {
            basketUseCase.checkoutBasket(products)
        }
    }
}

sealed class BasketEvent {
    object ShowSnackBar : BasketEvent()
}

sealed class BasketAction {
    data class RemoveProduct(val product: Product) : BasketAction()
    data class CheckoutBasket(val products: List<BasketProduct>) : BasketAction()
}