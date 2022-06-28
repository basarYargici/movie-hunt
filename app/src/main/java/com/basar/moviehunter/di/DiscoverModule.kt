package com.basar.moviehunter.di

import com.basar.moviehunter.data.remote.repository.DiscoverRepository
import com.basar.moviehunter.data.remote.repository.DiscoverRepositoryImpl
import com.basar.moviehunter.data.remote.service.DiscoverService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DiscoverModule {
    @Provides
    @Singleton
    fun provideDiscoverService(
        retrofit: Retrofit
    ): DiscoverService = retrofit.create(DiscoverService::class.java)

    @Provides
    @Singleton
    fun provideDiscoverRepository(
        service: DiscoverService
    ): DiscoverRepository = DiscoverRepositoryImpl(service)
}