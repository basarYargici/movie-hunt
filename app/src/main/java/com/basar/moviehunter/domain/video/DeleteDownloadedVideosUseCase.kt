package com.basar.moviehunter.domain.video

import com.basar.moviehunter.util.ConstantsHelper
import com.example.core.base.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import java.io.File
import javax.inject.Inject

class DeleteDownloadedVideosUseCase @Inject constructor() : UseCase<Any?, Boolean>() {
    private val downloadedMoviesFolder = File(ConstantsHelper.DOWNLOADED_VIDEO_PATH)

    override fun execute(params: Any?): Flow<Boolean> {
        return flow {
            val listFiles = downloadedMoviesFolder.listFiles()
            if (listFiles.isNullOrEmpty()) {
                emit(false)
            } else {
                listFiles.forEach {
                    Timber.d(it.name.toString())
                    Timber.d(it.totalSpace.toString())
                    it.delete()
                }
                emit(true)
            }
        }.flowOn(Dispatchers.IO)
    }
}