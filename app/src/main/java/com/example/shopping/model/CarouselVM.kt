package com.example.shopping.model

import androidx.navigation.NavHostController
import com.example.domain.model.Carousel
import com.example.domain.model.Product
import com.example.shopping.delegate.ProductDelegate

class CarouselVM(model: Carousel, private val productDelegate: ProductDelegate) :
    PresentationVM<Carousel>(model) {
    fun openCarouselProduct(navHostController: NavHostController, product: Product) {
        productDelegate.openProduct(navHostController, product)
        sendCarouselLog()
    }

    fun sendCarouselLog() {

    }
}