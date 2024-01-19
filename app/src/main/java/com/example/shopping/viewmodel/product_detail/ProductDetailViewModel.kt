package com.example.shopping.viewmodel.product_detail

import androidx.lifecycle.ViewModel
import com.example.domain.model.Product
import com.example.domain.usecase.ProductDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val useCase: ProductDetailUseCase
) : ViewModel() {
    private val _product = MutableStateFlow<Product?>(null)
    val product : StateFlow<Product?> = _product.asStateFlow()

    suspend fun updateProduct(productId: String){
        useCase.getProductDetail(productId = productId).collectLatest {
            _product.emit(it)
        }
    }

    fun addCard(productId: String){

    }

}