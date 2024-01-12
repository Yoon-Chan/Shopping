package com.example.shopping.ui.main

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.shopping.ui.common.ProductCard
import com.example.shopping.viewmodel.MainViewModel

@Composable
fun MainInsideScreen(viewModel: MainViewModel) {
    val productList by viewModel.productList.collectAsState(initial = emptyList())
    Log.e("MainInsideScreen", "${productList}")
    LazyColumn(){

        items(productList.size, key = {index -> productList[index].productId }){
            ProductCard(product = productList[it]){
                //상세화면 개발 시 추가
            }
        }

    }
}