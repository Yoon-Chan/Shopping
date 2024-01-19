package com.example.shopping.ui.category

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.domain.model.Category
import com.example.domain.model.Product
import com.example.shopping.ui.component.ProductCard
import com.example.shopping.viewmodel.category.CategoryViewModel

@Composable
fun CategoryScreen(
    category: Category,
    viewModel: CategoryViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val products by viewModel.products.collectAsState()
    LaunchedEffect(key1 = category) {
        viewModel.updateCategory(category = category)
    }

    LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(10.dp)) {
        items(products.size) {
            ProductCard(navHostController = navHostController, presentationVM = products[it])
        }
    }
}