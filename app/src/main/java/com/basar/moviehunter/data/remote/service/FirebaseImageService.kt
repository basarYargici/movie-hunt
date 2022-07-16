package com.basar.moviehunter.data.remote.service

import android.net.Uri
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class FirebaseImageService(
    private val storageRef: StorageReference
) {

    fun uploadImageToStorage(id: Int? = null, uri: Uri): Flow<Boolean> =
        flow {
            val task = storageRef.child("image/$id").putFile(
                uri
            ).await().task
            emit(
                task.isSuccessful
            )
        }
}