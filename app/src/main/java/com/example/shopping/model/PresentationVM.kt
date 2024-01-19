package com.example.shopping.model

import com.example.domain.model.BaseModel

sealed class PresentationVM<T: BaseModel>(val model : T) {
    //some func...
}