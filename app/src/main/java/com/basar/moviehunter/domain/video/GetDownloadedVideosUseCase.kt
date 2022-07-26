package com.basar.moviehunter.domain.video

import com.basar.moviehunter.data.model.DownloadedMovie
import com.basar.moviehunter.util.ConstantsHelper
import com.example.core.base.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.File
import javax.inject.Inject

class GetDownloadedVideosUseCase @Inject constructor() : UseCase<Any?, List<DownloadedMovie>>() {
    private val downloadedMoviesFolder = File(ConstantsHelper.DOWNLOADED_VIDEO_PATH)
    private val downloadedMoviesFiles: Array<File>? = downloadedMoviesFolder.listFiles()
    private val downloadedMoviesList = arrayListOf<DownloadedMovie>()

    override fun execute(params: Any?): Flow<List<DownloadedMovie>> {
        return flow {
            downloadedMoviesList.clear()
            downloadedMoviesFiles?.let {
                it.filter { file ->
                    file.extension == "mp4"
                }.forEach { file ->
                    downloadedMoviesList.add(DownloadedMovie(file.path))
                }
                downloadedMoviesList
            }
            emit(downloadedMoviesList)
        }.flowOn(Dispatchers.IO)
    }
}