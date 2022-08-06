package com.basar.moviehunter.domain.video

import com.basar.moviehunter.domain.video.DeleteSpecificDownloadedVideoUseCase.Params
import com.basar.moviehunter.util.ConstantsHelper
import com.example.core.base.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.File
import javax.inject.Inject

class DeleteSpecificDownloadedVideoUseCase @Inject constructor() :
    UseCase<Params, Boolean>() {
    private val downloadedMoviesFolder = File(ConstantsHelper.DOWNLOADED_VIDEO_PATH)

    data class Params(
        var moviePath: String
    )

    override fun execute(params: Params): Flow<Boolean> {
        return flow {
            val listFiles = downloadedMoviesFolder.listFiles()
            if (listFiles.isNullOrEmpty()) {
                emit(false)
            } else {
                listFiles.find {
                    it.path == params.moviePath
                }?.delete()
                emit(true)
            }
        }.flowOn(Dispatchers.IO)
    }
}