package com.basar.moviehunter.di

import com.basar.moviehunter.data.remote.repository.FirebaseStorageRepositoryImpl
import com.basar.moviehunter.data.remote.repository.ImageStorageRepository
import com.basar.moviehunter.data.remote.service.FirebaseImageService
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ImageStorageModule {

    @Singleton
    @Provides
    fun provideFirebaseStorageReference(): StorageReference = Firebase.storage.reference

    @Provides
    @Singleton
    fun provideFirebaseImageService(
        storageRef: StorageReference
    ): FirebaseImageService = FirebaseImageService(storageRef)

    @Provides
    @Singleton
    fun provideFirebaseImageRepository(
        service: FirebaseImageService
    ): ImageStorageRepository = FirebaseStorageRepositoryImpl(service)
}