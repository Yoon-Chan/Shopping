package com.example.shopping.utils

import java.text.DecimalFormat
import java.text.NumberFormat

object NumberUtils {
    fun numberFormatPrice(price: Int) : String{
        return DecimalFormat("#,###").format(price)
    }
}