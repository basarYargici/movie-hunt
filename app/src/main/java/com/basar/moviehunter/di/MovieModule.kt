package com.basar.moviehunter.di

import com.basar.moviehunter.data.remote.repository.MovieRepository
import com.basar.moviehunter.data.remote.repository.MovieRepositoryImpl
import com.basar.moviehunter.data.remote.service.MovieService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieModule {

    @Provides
    @Singleton
    fun provideMovieService(
        retrofit: Retrofit
    ): MovieService = retrofit.create(MovieService::class.java)

    @Provides
    @Singleton
    fun provideMovieRepository(
        service: MovieService
    ): MovieRepository = MovieRepositoryImpl(service)
}