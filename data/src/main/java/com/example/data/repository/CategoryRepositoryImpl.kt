package com.example.data.repository

import com.example.data.datasource.ProductDataSource
import com.example.data.db.dao.LikeDao
import com.example.data.db.entity.toLikeProductEntity
import com.example.domain.model.Category
import com.example.domain.model.Product
import com.example.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val dataSource: ProductDataSource,
    private val likeDao: LikeDao
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

    override fun getProductsByCategory(category: Category): Flow<List<Product>> =
        dataSource.getHomeComponents().map { list ->
            list.filterIsInstance<Product>().filter { product -> product.category.categoryId == category.categoryId }
        }.combine(likeDao.getAll()) { products, likeList ->
            products.map { product -> updateLikeStatus(product, likeList.map { it.productId }) }
        }

    override suspend fun likeProduct(product: Product) {
        if(product.isLike){
            likeDao.delete(product.productId)
        }else {
            likeDao.insert(product.toLikeProductEntity())
        }
    }

    private fun updateLikeStatus(product: Product, likeProductIds: List<String>) : Product {
        return product.copy(isLike = likeProductIds.contains(product.productId))
    }
}