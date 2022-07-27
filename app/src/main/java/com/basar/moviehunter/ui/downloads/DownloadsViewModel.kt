package com.basar.moviehunter.ui.downloads

import com.basar.moviehunter.base.BaseViewModel
import com.basar.moviehunter.data.model.DownloadedMovie
import com.basar.moviehunter.domain.video.GetDownloadedVideosUseCase
import com.basar.moviehunter.extension.launchInIO
import com.basar.moviehunter.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class DownloadsViewModel @Inject constructor(
    private val downloadedVideosUseCase: GetDownloadedVideosUseCase,
) : BaseViewModel() {
    val downloadedMoviesUI = SingleLiveEvent<List<DownloadedMovie>>()
    val hasDownloadedMovie = SingleLiveEvent<Boolean>()

    fun initVM() {
        getVideos()
    }

    private fun getVideos() = launchInIO {
        downloadedVideosUseCase(null)
            .onStart {
                showShimmer()
            }.onCompletion {
                delay(300L)
                hideShimmer()
            }.collect {
                downloadedMoviesUI.postValue(it)
                hasDownloadedMovie.postValue(it.isNotEmpty())
            }
    }
}