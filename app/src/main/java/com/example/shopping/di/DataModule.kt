package com.example.shopping.di

import com.example.data.repository.CategoryRepositoryImpl
import com.example.data.repository.MainRepositoryImpl
import com.example.data.repository.ProductDetailRepositoryImpl
import com.example.data.repository.TempRepositoryImpl
import com.example.domain.repository.CategoryRepository
import com.example.domain.repository.MainRepository
import com.example.domain.repository.ProductDetailRepository
import com.example.domain.repository.TempRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindTempRepository(tempRepositoryImpl: TempRepositoryImpl): TempRepository

    @Binds
    @Singleton
    abstract fun bindMainRepository(mainRepositoryImpl: MainRepositoryImpl): MainRepository

    @Binds
    @Singleton
    abstract fun bindCategoryRepository(categoryRepositoryImpl: CategoryRepositoryImpl): CategoryRepository

    @Binds
    @Singleton
    abstract fun bindProductDetailRepository(productDetailRepository: ProductDetailRepositoryImpl) : ProductDetailRepository
}