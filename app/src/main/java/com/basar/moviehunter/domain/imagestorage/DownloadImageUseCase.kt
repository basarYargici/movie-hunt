package com.basar.moviehunter.domain.imagestorage

import android.graphics.BitmapFactory
import com.basar.moviehunter.data.remote.repository.ImageStorageRepository
import com.basar.moviehunter.domain.uimodel.MyListUI
import com.basar.moviehunter.util.ConstantsHelper
import com.basar.moviehunter.util.Mapper
import com.example.core.base.UseCase
import com.google.firebase.storage.ListResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class DownloadImageUseCase @Inject constructor(
    val repository: ImageStorageRepository
) : UseCase<Any?, List<MyListUI>>() {
    var movieList = arrayListOf<MyListUI>()

    override fun execute(params: Any?): Flow<List<MyListUI>> {
        return repository.downloadImages().map(::response2UI)
    }

    private fun response2UI(response: ListResult): List<MyListUI> {
        return object : Mapper<ListResult, List<MyListUI>>() {
            override fun map(value: ListResult): List<MyListUI> {
                value.items.forEach { result ->
                    result.getBytes(ConstantsHelper.MAX_DOWNLOAD_SIZE).addOnFailureListener {
                        Timber.d("failure" + it.message)
                    }.addOnSuccessListener {
                        Timber.d("success" + it.size.toString())
                        val id = result.name
                        val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
                        movieList.add(MyListUI(id, bitmap))
                    }
                }
                return movieList
            }
        }.map(response)
    }
}