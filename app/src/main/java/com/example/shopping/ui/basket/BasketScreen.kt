package com.example.shopping.ui.basket

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.domain.model.BasketProduct
import com.example.domain.model.Product
import com.example.shopping.R
import com.example.shopping.ui.component.Price
import com.example.shopping.ui.theme.Purple40
import com.example.shopping.utils.NumberUtils
import com.example.shopping.viewmodel.basket.BasketViewModel

@Composable
fun BasketScreen(viewModel: BasketViewModel = hiltViewModel()) {
    val basketProducts by viewModel.basketProducts.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f), contentPadding = PaddingValues(10.dp)
        ) {
            items(basketProducts.size) { index ->
                BasketProductCard(basketProduct = basketProducts[index]) {
                    viewModel.removeBasketProduct(it)
                }
            }
        }
        Button(onClick = { /*TODO*/ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Purple40)) {
                Icon(Icons.Filled.Check, contentDescription = "CheckIcons")
            Text(text = "${getTotalPrice(basketProducts)}원 결제하기", modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp), fontSize = 16.sp
            )
        }
    }
}

@Composable
fun BasketProductCard(basketProduct: BasketProduct, removeProduct: (Product) -> Unit) {
    Box(Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.product_image),
                contentDescription = "productImage",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(120.dp)
                    .aspectRatio(1f)
            )
            Column(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .weight(1f)
            ) {
                Text(
                    text = basketProduct.product.shop.shopName,
                    fontSize = 14.sp,
                )
                Text(
                    text = basketProduct.product.productName,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 10.dp)
                )
                Price(product = basketProduct.product)
            }
            Text(
                text = "${basketProduct.count} 개",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(30.dp)
            )
        }
        IconButton(
            onClick = { removeProduct(basketProduct.product) },
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Icon(Icons.Default.Close, contentDescription = "CloseIcon")
        }
    }
}

private fun getTotalPrice(list: List<BasketProduct>) : String {
    val totalPrice = list.sumOf { it.product.price.finalPrice * it.count }
    return NumberUtils.numberFormatPrice(totalPrice)
}