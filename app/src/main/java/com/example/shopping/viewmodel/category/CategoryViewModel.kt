package com.example.shopping.viewmodel.category

import androidx.lifecycle.ViewModel
import com.example.domain.model.BaseModel
import com.example.domain.model.Category
import com.example.domain.model.Product
import com.example.domain.usecase.CategoryUseCase
import com.example.shopping.delegate.ProductDelegate
import com.example.shopping.model.ProductVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val useCase : CategoryUseCase
): ViewModel(), ProductDelegate {
    private val _products = MutableStateFlow<List<ProductVM>>(listOf())
    val products : StateFlow<List<ProductVM>> = _products.asStateFlow()

    suspend fun updateCategory(category: Category){
        useCase.getProductByCategory(category = category).collectLatest {
            _products.emit(convertToPresentationVM(it))
        }
    }

    override fun openProduct(product: Product) {

    }

    private fun convertToPresentationVM(list: List<Product>) : List<ProductVM> {
        return list.map {
            ProductVM(it,this)
        }
    }
}