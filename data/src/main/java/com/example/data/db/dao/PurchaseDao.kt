package com.example.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.db.entity.BasketProductEntity

@Dao
interface PurchaseDao {

    @Query("SELECT * FROM purchase")
    suspend fun getAll() : List<BasketProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: BasketProductEntity)

    @Query("DELETE FROM purchase WHERE productId=:id")
    suspend fun delete(id: String)

}