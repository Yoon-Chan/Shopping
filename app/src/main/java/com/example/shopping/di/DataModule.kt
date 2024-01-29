package com.example.shopping.di

import com.example.data.repository.AccountRepositoryImpl
import com.example.data.repository.CategoryRepositoryImpl
import com.example.data.repository.LikeRepositoryImpl
import com.example.data.repository.MainRepositoryImpl
import com.example.data.repository.ProductDetailRepositoryImpl
import com.example.data.repository.SearchRepositoryImpl
import com.example.data.repository.TempRepositoryImpl
import com.example.domain.repository.AccountRepository
import com.example.domain.repository.CategoryRepository
import com.example.domain.repository.LikeRepository
import com.example.domain.repository.MainRepository
import com.example.domain.repository.ProductDetailRepository
import com.example.domain.repository.SearchRepository
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

    @Binds
    @Singleton
    abstract fun bindSearchRepository(searchRepositoryImpl: SearchRepositoryImpl) : SearchRepository

    @Binds
    @Singleton
    abstract fun bindAccountRepository(accountRepositoryImpl: AccountRepositoryImpl) : AccountRepository

    @Binds
    @Singleton
    abstract fun bindLikeRepository(likeRepositoryImpl: LikeRepositoryImpl) : LikeRepository
}