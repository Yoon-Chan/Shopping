package com.example.domain.usecase

import com.example.domain.model.TempModel
import com.example.domain.repository.TempRepository
import javax.inject.Inject

class TempUseCase @Inject constructor(
    private val repository: TempRepository
) {

    fun getTempModel(): TempModel {
        return repository.getTempModel()
    }
}