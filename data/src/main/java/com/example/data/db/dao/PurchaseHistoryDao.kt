package com.example.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.db.entity.BasketProductEntity
import com.example.data.db.entity.PurchaseHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PurchaseHistoryDao {
    @Query("SELECT * FROM history")
    fun getAll() : Flow<List<PurchaseHistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: PurchaseHistoryEntity)

    @Query("SELECT * FROM history WHERE id=:id")
    suspend fun get(id: Int): PurchaseHistoryEntity?

    @Query("DELETE FROM history WHERE id=:id")
    suspend fun delete(id: Int)

    @Query("DELETE FROM history")
    suspend fun deleteAll()
}