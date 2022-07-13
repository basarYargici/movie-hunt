package com.basar.moviehunter.ui.downloads

import androidx.lifecycle.MutableLiveData
import com.basar.moviehunter.base.BaseViewModel
import com.basar.moviehunter.data.model.DownloadedMovie
import com.basar.moviehunter.domain.video.GetDownloadedVideosUseCase
import com.basar.moviehunter.extension.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class DownloadsViewModel @Inject constructor(
    private val downloadedVideosUseCase: GetDownloadedVideosUseCase,
) : BaseViewModel() {
    val downloadedMoviesUI = MutableLiveData<List<DownloadedMovie>>()

    fun initVM() {
        getVideos()
    }

    private fun getVideos() = launch {
        downloadedVideosUseCase(null)
            .onStart {
                // TODO:
            }.onCompletion {
                // TODO:
            }.collect {
                downloadedMoviesUI.postValue(it)
            }
    }
}