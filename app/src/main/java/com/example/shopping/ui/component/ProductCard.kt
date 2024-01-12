package com.example.shopping.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.model.Category
import com.example.domain.model.Product
import com.example.domain.model.Shop
import com.example.shopping.R
import com.example.domain.model.Price

@Composable
fun ProductCard(product: Product, onClick: (Product) -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(intrinsicSize = IntrinsicSize.Max)
            .padding(8.dp)
            .shadow(elevation = 10.dp),

        ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Image(
                painter = painterResource(id = R.drawable.product_image),
                contentDescription = "product_image",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )

            Text(fontSize = 14.sp, fontWeight = FontWeight.SemiBold, text = product.shop.shopName)
            Text(fontSize = 14.sp, text = product.productName)
            Price(product = product)
        }
    }
}

@Composable
fun Price(product: Product) {
    when (product.price.salesStatus) {
        "ON_SALE" -> {
            Text(
                fontSize = 14.sp,
                text = "${product.price.originPrice}원",
                style = TextStyle(textDecoration = TextDecoration.LineThrough)
            )
            Row {
                Text(text = "할인가: ", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(
                    text = "${product.price.finalPrice}원",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }

        "ON_DISCOUNT" -> {
            Text(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                text = "${product.price.originPrice}"
            )
        }

        "SOLD_OUT" -> {
            Text(text = "판매 종료", fontSize = 18.sp, color = Color(0xFF666666))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProductCard() {
    ProductCard(product = Product(
        "1",
        "상품 이름",
        price = Price(3000, 3000, "ON_SALE"),
        category = Category.Top,
        shop = Shop("1", "shopName", "shopUrl"),
        imageUrl = "imageUrl",
        isFreeShipping = true,
        isNew = true
        ),
        onClick = {}
    )
}


@Preview(showBackground = true)
@Composable
fun PreviewProductCardDiscount() {
    ProductCard(product = Product(
        "1",
        "상품 이름",
        price = Price(3000, 3000,"ON_DISCOUNT"),
        category = Category.Top,
        shop = Shop("1", "shopName", "shopUrl"),
        imageUrl = "imageUrl",
        isFreeShipping = true,
        isNew = true
    ),
        onClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewProductCardSoldOut() {
    ProductCard(product = Product(
        "1",
        "상품 이름",
        price = Price(3000, 3000, "SOLD_OUT"),
        category = Category.Top,
        shop = Shop("1", "shopName", "shopUrl"),
        imageUrl = "imageUrl",
        isFreeShipping = true,
        isNew = true
    ),
        onClick = {}
    )
}