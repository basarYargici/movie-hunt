package com.basar.moviehunter.di

import com.basar.moviehunter.data.remote.repository.VideoRepository
import com.basar.moviehunter.data.remote.repository.VideoRepositoryImpl
import com.basar.moviehunter.data.remote.service.MovieVideoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object VideoModule {

    @Provides
    @Singleton
    fun provideVideoService(
        retrofit: Retrofit
    ): MovieVideoService = retrofit.create(MovieVideoService::class.java)

    @Provides
    @Singleton
    fun provideVideoRepository(
        service: MovieVideoService
    ): VideoRepository = VideoRepositoryImpl(service)
}