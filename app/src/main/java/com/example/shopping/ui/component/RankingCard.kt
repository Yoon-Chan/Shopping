package com.example.shopping.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.domain.model.Product
import com.example.domain.model.Ranking
import com.example.shopping.R
import com.example.shopping.model.RankingVM

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RankingCard(navHostController: NavHostController, presentationVM: RankingVM) {
    val pageCount = presentationVM.model.productList.size / DEFAULT_RANKING_ITEM_COUNT
    val pagerState = rememberPagerState {
        pageCount
    }
    Column {
        Text(presentationVM.model.title, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(end = 50.dp)
        ) { index ->
            val rank = index * DEFAULT_RANKING_ITEM_COUNT
            Column {
                repeat(DEFAULT_RANKING_ITEM_COUNT){
                    RankingProductCard(
                        rank + it,
                        presentationVM.model.productList[rank + it],
                        presentationVM = presentationVM,
                        onClick = {product ->
                            presentationVM.openRankingProduct(navHostController,product)
                        }
                    )
                }
            }
        }
    }

}

@Composable
fun RankingProductCard(index: Int, model: Product,presentationVM : RankingVM ,onClick: (Product) -> Unit) {
    Box(modifier = Modifier.fillMaxWidth()) {
        IconButton(
            onClick = { presentationVM.likeProduct(model) },
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            Icon(
                imageVector = if (model.isLike) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                contentDescription = "좋아요 이미지"
            )
        }
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "${index + 1}", fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.product_image),
                contentDescription = "productImage",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(70.dp)
                    .aspectRatio(0.7f)
                    .clickable { onClick(model) }
            )
            Column(modifier = Modifier.padding(start = 10.dp)) {
                Text(
                    text = model.shop.shopName,
                    fontSize = 14.sp,
                )
                Text(
                    text = model.productName,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 10.dp)
                )
                Price(product = model)
            }
        }
    }
}

private const val DEFAULT_RANKING_ITEM_COUNT = 3