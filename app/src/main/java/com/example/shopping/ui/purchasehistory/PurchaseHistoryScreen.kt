package com.example.shopping.ui.purchasehistory

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.domain.model.BasketProduct
import com.example.domain.model.PurchaseHistory
import com.example.shopping.R
import com.example.shopping.ui.component.Price
import com.example.shopping.utils.NumberUtils
import com.example.shopping.viewmodel.purchasehistory.PurchaseHistoryViewModel

@Composable
fun PurchaseHistoryScreen(viewModel: PurchaseHistoryViewModel = hiltViewModel()) {
    val purchaseHistory by viewModel.purchaseHistory.collectAsState(initial = emptyList())

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        items(purchaseHistory.size) { index ->
            PurchaseHistoryCard(purchaseHistory = purchaseHistory[index])
        }
    }
}

@Composable
fun PurchaseHistoryCard(purchaseHistory: PurchaseHistory) {
    LazyColumn(modifier = Modifier.fillMaxWidth(), contentPadding = PaddingValues(10.dp)) {
        item {
            Text(text = "결제 시기: ${purchaseHistory.purchaseAt}", fontSize = 16.sp)
        }
        items(purchaseHistory.basketList.size) { index ->
            val currentItem = purchaseHistory.basketList[index]
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.product_image),
                    contentDescription = "purchaseItem",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(60.dp)
                )
                Column(
                    Modifier
                        .padding(10.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = "${currentItem.product.shop.shopName} - ${currentItem.product.productName}",
                        fontSize = 14.sp,
                        modifier = Modifier.padding(10.dp)
                    )
                    Price(product = currentItem.product)
                }
                Text(
                    text = "${currentItem.count} 개",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(30.dp)
                )
            }
        }
        item {
            Text(
                text = "${getTotalPrice(purchaseHistory.basketList)} 결제 완료", modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp), fontSize = 16.sp
            )
        }
    }
}

private fun getTotalPrice(list: List<BasketProduct>): String {
    val totalPrice = list.sumOf { it.product.price.finalPrice * it.count }
    return NumberUtils.numberFormatPrice(totalPrice)
}