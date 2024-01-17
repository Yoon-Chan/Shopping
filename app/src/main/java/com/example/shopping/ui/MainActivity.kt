package com.example.shopping.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.shopping.ui.theme.ShoppingTheme
import com.example.shopping.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreen()
                }
            }
        }
        viewModel.updateColumnCount(getColumnCount())
    }

    private fun getColumnCount() : Int {
        return getDisplayWidthDp().toInt() / DEFAULT_COLUMN_SIZE
    }

    private fun getDisplayWidthDp() : Float {
        return resources.displayMetrics.run { widthPixels / density }
    }

    companion object {
        private const val DEFAULT_COLUMN_SIZE = 120
    }
}