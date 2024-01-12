package com.example.shopping.ui.component

import android.view.MotionEvent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.widget.ConstraintSet.Motion
import com.example.domain.model.BannerList
import com.example.shopping.R
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun BannerListCard(model :BannerList){
    val pagerState = rememberPagerState {
        model.imageList.size
    }
    LaunchedEffect(key1 = pagerState){
        autoScrollInfinity(pagerState = pagerState)
    }

    HorizontalPager(state = pagerState) {
        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .shadow(10.dp)
        ) {
            Box(
                contentAlignment = Alignment.TopEnd,
                modifier = Modifier.fillMaxWidth()) {
                Text(text = "pageNumber : $it")
            }
            Image(painter = painterResource(id = R.drawable.product_image), contentDescription = "description",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f)
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
private suspend fun autoScrollInfinity(pagerState: PagerState) {
    while (true){
        delay(3000)
        val currentPage = pagerState.currentPage
        pagerState.animateScrollToPage((currentPage + 1) % pagerState.pageCount)
    }
}