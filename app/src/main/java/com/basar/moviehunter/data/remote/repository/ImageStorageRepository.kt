package com.basar.moviehunter.data.remote.repository

import android.net.Uri
import com.basar.moviehunter.base.BaseRepository
import com.basar.moviehunter.data.remote.service.FirebaseImageService
import com.google.firebase.storage.ListResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ImageStorageRepository {
    fun uploadImage(id: Int? = null, uri: Uri): Flow<Boolean>
    fun downloadImages(): Flow<ListResult>
}

class FirebaseStorageRepositoryImpl @Inject constructor(
    private val firebaseImageService: FirebaseImageService
) : BaseRepository(), ImageStorageRepository {
    override fun uploadImage(id: Int?, uri: Uri): Flow<Boolean> {
        return firebaseImageService.uploadImageToStorage(id, uri)
    }

    override fun downloadImages(): Flow<ListResult> {
        return firebaseImageService.downloadImage()
    }
}