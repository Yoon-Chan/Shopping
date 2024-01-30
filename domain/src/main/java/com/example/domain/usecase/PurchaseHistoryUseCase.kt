package com.example.domain.usecase

import com.example.domain.model.PurchaseHistory
import com.example.domain.repository.PurchaseHistoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PurchaseHistoryUseCase @Inject constructor(
    private val repository: PurchaseHistoryRepository
) {
    fun getPurchaseHistory(): Flow<List<PurchaseHistory>> {
        return repository.getPurchaseHistory()
    }
}