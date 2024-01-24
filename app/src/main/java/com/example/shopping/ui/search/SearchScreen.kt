package com.example.shopping.ui.search

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.RangeSlider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.domain.model.SearchFilter
import com.example.shopping.ui.component.ProductCard
import com.example.shopping.ui.theme.Purple40
import com.example.shopping.ui.theme.Purple80
import com.example.shopping.viewmodel.search.SearchViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchScreen(
    navHostController: NavHostController,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val searchFilter by viewModel.searchFilters.collectAsState()
    val sheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)
    val scope = rememberCoroutineScope()
    var currentFilterType by remember {
        mutableStateOf<SearchFilter.Type?>(null)
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetShape = RoundedCornerShape(10.dp, 10.dp, 0.dp, 0.dp),
        sheetContent = {
            currentFilterType?.let { type ->
                when (type) {
                    SearchFilter.Type.CATEGORY -> {
                        scope.launch {
                            sheetState.expand()
                        }
                        val categoryFilter =
                            searchFilter.first { it is SearchFilter.CategoryFilter } as SearchFilter.CategoryFilter
                        SearchFilterCategoryContent(filter = categoryFilter) {
                            scope.launch {
                                currentFilterType = null
                                sheetState.collapse()
                            }
                            viewModel.updateFilter(it)
                        }
                    }

                    SearchFilter.Type.PRICE -> {
                        scope.launch {
                            sheetState.expand()
                        }
                        val priceFilter =
                            searchFilter.first { it is SearchFilter.PriceFilter } as SearchFilter.PriceFilter
                        SearchFilterPriceContent(filter = priceFilter) {
                            scope.launch {
                                currentFilterType = null
                                sheetState.collapse()
                            }
                            viewModel.updateFilter(it)
                        }
                    }
                }
            }

        },
        sheetPeekHeight = 0.dp
    ) {
        SearchContent(viewModel = viewModel, navHostController = navHostController) {
            currentFilterType = it
        }
    }
}

@Composable
fun SearchFilterCategoryContent(
    filter: SearchFilter.CategoryFilter,
    onCompleteFilter: (SearchFilter) -> Unit
) {
    //헤더
    //카테고리 리스트
    Column(
        Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        Text(
            text = "카테고리 필터",
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            modifier = Modifier.padding(10.dp)
        )
        LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(10.dp)) {
            items(filter.category.size) { index ->
                val category = filter.category[index]
                Button(
                    onClick = {
                        filter.selectedCategory = category
                        onCompleteFilter(filter)
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = if (filter.selectedCategory == category) Purple80 else Purple40)
                ) {
                    Text(text = category.categoryName, fontSize = 18.sp)
                }
            }
        }
    }
}

@Composable
fun SearchFilterPriceContent(
    filter: SearchFilter.PriceFilter,
    onCompleteFilter: (SearchFilter) -> Unit
) {
    var sliderValues by remember {
        val selectedRange = filter.selectedRange
        if (selectedRange == null) {
            mutableStateOf(filter.priceRange.first..filter.priceRange.second)
        } else {
            mutableStateOf(selectedRange.first..selectedRange.second)
        }
    }
    //헤더
    //카테고리 리스트
    Column(
        Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(20.dp)
    ) {
        Row(Modifier.fillMaxWidth()) {
            Text(
                text = "가격 필터",
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                modifier = Modifier.padding(10.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    filter.selectedRange = sliderValues.start to sliderValues.endInclusive
                    onCompleteFilter(filter)
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Purple80)
            ) {
                Text(text = "완료", fontSize = 18.sp)
            }
        }
        RangeSlider(
            value = sliderValues,
            onValueChange = { sliderValues = it },
            valueRange = filter.priceRange.first..filter.priceRange.second,
            steps = 9
        )
        Text(text = "최저가: ${sliderValues.start} ~ 최고가: ${sliderValues.endInclusive}")
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchContent(
    viewModel: SearchViewModel,
    navHostController: NavHostController,
    openFilterDialog: (SearchFilter.Type) -> Unit
) {
    val searchResult by viewModel.searchResult.collectAsState()
    val searchFilters by viewModel.searchFilters.collectAsState()
    val searchKeywords by viewModel.searchKeywords.collectAsState(initial = emptyList())
    var keyword by remember {
        mutableStateOf("")
    }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column {
        //검색바
        SearchBox(
            keyword = keyword,
            onValueChange = { keyword = it },
            searchAction = {
                viewModel.search(keyword)
                keyboardController?.hide()
            })
        //검색 결과 or 최근 검색어

        if (searchResult.isEmpty()) {
            Text(
                text = "최근 검색어",
                modifier = Modifier.padding(6.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(10.dp)) {
                items(searchKeywords.size) { index ->
                    val currentKeyword = searchKeywords.reversed()[index].keyword
                    Button(
                        onClick = {
                            keyword = currentKeyword
                            viewModel.search(keyword)
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Unspecified)
                    ) {
                        Text(text = currentKeyword, fontSize = 18.sp)
                    }

                }
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Button(onClick = { openFilterDialog(SearchFilter.Type.CATEGORY) }) {
                    val filter =
                        searchFilters.find { it.type == SearchFilter.Type.CATEGORY } as? SearchFilter.CategoryFilter
                    if (filter?.selectedCategory == null) {
                        Text(text = "Category")
                    } else {
                        Text(text = "${filter.selectedCategory?.categoryName}")
                    }
                }
                Spacer(modifier = Modifier.width(20.dp))
                Button(onClick = { openFilterDialog(SearchFilter.Type.PRICE) }) {
                    val filter =
                        searchFilters.find { it.type == SearchFilter.Type.PRICE } as? SearchFilter.PriceFilter
                    if (filter?.selectedRange == null) {
                        Text(text = "Price")
                    } else {
                        Text(text = "${filter.selectedRange?.first} ~ ${filter.selectedRange?.second}")
                    }
                }
            }

            LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(10.dp)) {
                items(searchResult.size) { index ->
                    ProductCard(
                        navHostController = navHostController,
                        presentationVM = searchResult[index]
                    )

                }
            }
        }
    }
}


@Composable
fun SearchBox(keyword: String, onValueChange: (String) -> Unit, searchAction: () -> Unit) {
    Row(Modifier.fillMaxWidth()) {
        TextField(
            value = keyword,
            onValueChange = onValueChange,
            placeholder = { Text(text = "검색어를 입력해주세요") },
            shape = RoundedCornerShape(8.dp),
            keyboardActions = KeyboardActions(
                onSearch = { searchAction() }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            maxLines = 1,
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "SearchIcon"
                )
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search,
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Words,
                autoCorrect = true
            )
        )
    }

}