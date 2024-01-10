package com.example.data.repository

import com.example.data.datasource.TempDataSource
import com.example.domain.model.TempModel
import com.example.domain.repository.TempRepository
import javax.inject.Inject

class TempRepositoryImpl @Inject constructor(
    private val dataSource: TempDataSource
): TempRepository {
    override fun getTempModel(): TempModel {
        return dataSource.getTempModel()
    }
}