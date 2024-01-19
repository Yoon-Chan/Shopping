package com.example.shopping.model

import com.example.domain.model.Product
import com.example.domain.model.Ranking
import com.example.shopping.delegate.ProductDelegate

class RankingVM(model : Ranking, private val productDelegate: ProductDelegate) : PresentationVM<Ranking>(model) {
    fun openRankingProduct(product: Product) {
        productDelegate.openProduct(product)
        sendRankingLog()
        //+@
    }

    fun sendRankingLog() {

    }
}