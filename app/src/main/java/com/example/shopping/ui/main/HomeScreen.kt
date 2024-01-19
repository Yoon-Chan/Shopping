package com.example.shopping.ui.main

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.example.domain.model.Banner
import com.example.domain.model.BannerList
import com.example.domain.model.Carousel
import com.example.domain.model.ModelType
import com.example.domain.model.Product
import com.example.domain.model.Ranking
import com.example.shopping.model.BannerListVM
import com.example.shopping.model.BannerVM
import com.example.shopping.model.CarouselVM
import com.example.shopping.model.ProductVM
import com.example.shopping.model.RankingVM
import com.example.shopping.ui.component.BannerCard
import com.example.shopping.ui.component.BannerListCard
import com.example.shopping.ui.component.CarouselCard
import com.example.shopping.ui.component.ProductCard
import com.example.shopping.ui.component.RankingCard
import com.example.shopping.viewmodel.MainViewModel


@Composable
fun MainHomeInsideScreen(navHostController: NavHostController, viewModel: MainViewModel) {
    val modelList by viewModel.modelList.collectAsState(initial = emptyList())
    val columnCount by viewModel.columnCount.collectAsState()

    LazyVerticalGrid(GridCells.Fixed(columnCount)) {
        items(modelList.size, span = { index ->
            val item = modelList[index]
            val spanCount = getSpanCountByType(item.model.type, columnCount)
            GridItemSpan(spanCount)
        }) {
            when (val item = modelList[it]) {
                is BannerVM -> {
                    BannerCard(presentationVM = item)
                }

                is ProductVM -> {
                    ProductCard(navHostController = navHostController,presentationVM = item)
                }

                is BannerListVM -> {
                    BannerListCard(presentationVM = item)
                }

                is CarouselVM -> {
                    CarouselCard(navHostController = navHostController, presentationVM = item)
                }

                is RankingVM -> {
                    RankingCard(navHostController = navHostController, presentationVM = item)
                }
            }
        }
    }
}

private fun getSpanCountByType(type: ModelType, defaultColumnCount: Int): Int {
    return when (type) {
        ModelType.PRODUCT -> {
            1
        }

        ModelType.BANNER, ModelType.BANNER_LIST, ModelType.CAROUSEL, ModelType.RANKING -> {
            defaultColumnCount
        }
    }

}