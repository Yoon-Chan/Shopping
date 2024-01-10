package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.domain.model.TempModel
import com.example.domain.repository.TempRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TempViewModel @Inject constructor(
    private val repository: TempRepository
) : ViewModel() {

    fun getTempModel(): TempModel {
        return repository.getTempModel()
    }
}