package com.example.data.repository

import com.example.domain.model.Category
import com.example.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(

) : CategoryRepository {

    override fun getCategories(): Flow<List<Category>> {
        return flow {
            emit(
                listOf(
                    Category.Top,
                    Category.Bag,
                    Category.Outerwear,
                    Category.Dress,
                    Category.Pants,
                    Category.FashionAccessories,
                    Category.Skirt,
                    Category.Shoes,
                )
            )
        }
    }
}