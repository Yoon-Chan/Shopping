package com.example.data.datasource

import com.example.domain.model.TempModel
import javax.inject.Inject

class TempDataSource @Inject constructor(

) {
    fun getTempModel() : TempModel {
        return TempModel("testModel")
    }
}