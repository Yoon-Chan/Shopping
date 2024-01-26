package com.example.shopping.delegate

import androidx.navigation.NavHostController
import com.example.domain.model.Product

interface ProductDelegate {
    fun openProduct(navHostController: NavHostController ,product: Product)

    fun likeProduct(product: Product)
}