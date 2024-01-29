package com.example.shopping.ui.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.shopping.model.ProductVM
import com.example.shopping.ui.component.ProductCard
import com.example.shopping.viewmodel.MainViewModel

@Composable
fun LikeScreen(navHostController: NavHostController, viewModel: MainViewModel) {
    val likeProduct by viewModel.likeProduct.collectAsState(initial = emptyList())

    LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(10.dp)){
        items(likeProduct.size) {index ->
            ProductCard(navHostController = navHostController, presentationVM = likeProduct[index] as ProductVM)
        }
    }
}