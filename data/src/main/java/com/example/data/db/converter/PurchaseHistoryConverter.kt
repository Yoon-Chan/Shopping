package com.example.data.db.converter

import androidx.room.TypeConverter
import com.example.domain.model.BasketProduct
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class PurchaseHistoryConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromBasketProductList(value: List<BasketProduct>): String {
        return gson.toJson(value, object : TypeToken<List<BasketProduct>>() {}.type)
    }

    @TypeConverter
    fun toBasketProductList(value: String): List<BasketProduct> {
        return gson.fromJson(value, object : TypeToken<List<BasketProduct>>() {}.type)
    }

    @TypeConverter
    fun fromZoneDateTime(value: ZonedDateTime): String {
        return DateTimeFormatter.ISO_ZONED_DATE_TIME.format(value)
    }

    @TypeConverter
    fun toZoneDateTime(value: String): ZonedDateTime {
        return ZonedDateTime.parse(value)
    }

}