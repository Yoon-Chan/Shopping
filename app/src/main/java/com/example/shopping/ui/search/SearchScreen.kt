package com.example.shopping.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.domain.model.SearchKeyword
import com.example.shopping.ui.component.ProductCard
import com.example.shopping.viewmodel.search.SearchViewModel

@Composable
fun SearchScreen(
    navHostController: NavHostController,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val searchResult by viewModel.searchResult.collectAsState()
    val searchKeywords by viewModel.searchKeywords.collectAsState(initial = emptyList())
    var keyword by remember {
        mutableStateOf("")
    }

    Column {
        //검색바
        SearchBox(
            keyword = keyword,
            onValueChange = { keyword = it },
            searchAction = { viewModel.search(keyword) })
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