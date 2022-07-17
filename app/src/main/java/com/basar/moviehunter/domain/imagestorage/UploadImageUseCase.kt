package com.basar.moviehunter.domain.imagestorage

import android.net.Uri
import com.basar.moviehunter.data.remote.repository.ImageStorageRepository
import com.example.core.base.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UploadImageUseCase @Inject constructor(
    val repository: ImageStorageRepository
) : UseCase<UploadImageUseCase.Params, Boolean>() {

    data class Params(
        var id: Int? = null,
        var uri: Uri
    )

    override fun execute(params: Params): Flow<Boolean> {
        return repository.uploadImage(params.id, params.uri)
    }
}