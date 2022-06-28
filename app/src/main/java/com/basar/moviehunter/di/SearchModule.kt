package com.basar.moviehunter.di

import com.basar.moviehunter.data.remote.repository.SearchRepository
import com.basar.moviehunter.data.remote.repository.SearchRepositoryImpl
import com.basar.moviehunter.data.remote.service.SearchService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchModule {
    @Provides
    @Singleton
    fun provideSearchService(
        retrofit: Retrofit
    ): SearchService = retrofit.create(SearchService::class.java)

    @Provides
    @Singleton
    fun provideSearchRepository(
        service: SearchService
    ): SearchRepository = SearchRepositoryImpl(service)
}