package com.basar.moviehunter.ui.downloads

import androidx.lifecycle.viewModelScope
import com.basar.moviehunter.base.BaseViewModel
import com.basar.moviehunter.data.model.DownloadedMovie
import com.basar.moviehunter.domain.video.DeleteSpecificDownloadedVideoUseCase
import com.basar.moviehunter.domain.video.GetDownloadedVideosUseCase
import com.basar.moviehunter.extension.launch
import com.basar.moviehunter.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DownloadsViewModel @Inject constructor(
    private val downloadedVideosUseCase: GetDownloadedVideosUseCase,
    private val deleteSpecificDownloadedVideoUseCase: DeleteSpecificDownloadedVideoUseCase
) : BaseViewModel() {
    val downloadedMoviesUI = SingleLiveEvent<List<DownloadedMovie>>()
    val hasDownloadedMovie = SingleLiveEvent<Boolean>()
    val isMovieDeleted = SingleLiveEvent<Boolean>()
    var readPermissionGranted: Boolean = false

    fun initVM() {
        getVideos()
    }

    private fun getVideos() = viewModelScope.launch(Dispatchers.IO) {
        downloadedVideosUseCase(null)
            .onStart {
                showShimmer()
            }.onCompletion {
                hideShimmer()
            }.collect {
                delay(500L)
                downloadedMoviesUI.postValue(it)
                hasDownloadedMovie.postValue(it.isNotEmpty())
            }
    }

    fun deleteDownloads(moviePath: String) = launch {
        deleteSpecificDownloadedVideoUseCase(
            DeleteSpecificDownloadedVideoUseCase.Params(moviePath)
        ).onStart {
            showLoading()
        }.onCompletion {
            hideLoading()
        }.catch {
            Timber.d(this.toString())
        }.collect {
            isMovieDeleted.postValue(it)
        }
    }
}