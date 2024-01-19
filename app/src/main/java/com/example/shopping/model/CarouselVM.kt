package com.example.shopping.model

import com.example.domain.model.Carousel
import com.example.domain.model.Product
import com.example.shopping.delegate.ProductDelegate

class CarouselVM(model: Carousel, private val productDelegate: ProductDelegate) :
    PresentationVM<Carousel>(model) {
    fun openCarouselProduct(product: Product) {
        productDelegate.openProduct(product)
        sendCarouselLog()
    }

    fun sendCarouselLog() {

    }
}