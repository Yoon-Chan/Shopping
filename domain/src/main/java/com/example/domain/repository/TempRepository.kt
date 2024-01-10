package com.example.domain.repository

import com.example.domain.model.TempModel

interface TempRepository {
    fun getTempModel(): TempModel
}