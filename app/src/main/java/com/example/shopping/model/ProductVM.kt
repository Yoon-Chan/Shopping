package com.example.shopping.model

import com.example.domain.model.Product
import com.example.shopping.delegate.ProductDelegate

class ProductVM(model: Product, productDelegate: ProductDelegate) : PresentationVM<Product>(model),
    ProductDelegate by productDelegate {

}