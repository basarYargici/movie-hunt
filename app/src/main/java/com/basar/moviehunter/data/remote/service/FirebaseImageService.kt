package com.basar.moviehunter.data.remote.service

import android.net.Uri
import com.basar.moviehunter.util.ConstantsHelper.FIREBASE_IMAGE_PATH
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class FirebaseImageService(
    private val storageRef: StorageReference
) {

    fun uploadImageToStorage(id: Int? = null, uri: Uri): Flow<Boolean> =
        flow {
            val task = storageRef.child(FIREBASE_IMAGE_PATH + "$id").putFile(
                uri
            ).await().task
            emit(
                task.isSuccessful
            )
        }

    fun downloadImage(): Flow<ListResult> =
        flow {
            emit(
                storageRef.child(FIREBASE_IMAGE_PATH).listAll().await()
            )
        }
}