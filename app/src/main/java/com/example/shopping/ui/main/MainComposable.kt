package com.example.shopping.ui.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.shopping.ui.common.ProductCard
import com.example.shopping.viewmodel.MainViewModel


@Composable
fun MainInsideScreen(viewModel: MainViewModel) {
    val productList by viewModel.productList.collectAsState(initial = emptyList())
    val columnCount by viewModel.columnCount.collectAsState()

    LazyVerticalGrid(GridCells.Fixed(columnCount)){
        items(productList.size, key = {index -> productList[index].productId }){
            ProductCard(product = productList[it]){
                //상세화면 개발 시 추가
            }
        }
    }
}