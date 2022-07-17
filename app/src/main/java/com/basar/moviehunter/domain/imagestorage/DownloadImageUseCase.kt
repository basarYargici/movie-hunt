package com.basar.moviehunter.domain.imagestorage

import android.graphics.BitmapFactory
import com.basar.moviehunter.data.remote.repository.ImageStorageRepository
import com.basar.moviehunter.domain.uimodel.MyListUI
import com.basar.moviehunter.util.ConstantsHelper
import com.example.core.base.UseCase
import com.google.firebase.storage.ListResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject

class DownloadImageUseCase @Inject constructor(
    val repository: ImageStorageRepository
) : UseCase<Any?, ListResult>() {
    var movieList = arrayListOf<MyListUI>()

    override fun execute(params: Any?): Flow<ListResult> {
        return repository.downloadImages()
    }

    fun decodeImages(value: ListResult): Flow<ArrayList<MyListUI>> = flow {
        movieList.clear()
        value.items.forEach { result ->
            val byteArray: ByteArray = result.getBytes(ConstantsHelper.MAX_DOWNLOAD_SIZE).await()
            Timber.d("success" + byteArray.size.toString())
            val id = result.name.toInt()
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            movieList.add(MyListUI(id, bitmap))
        }
        emit(movieList)
    }
}