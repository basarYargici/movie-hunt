package com.basar.moviehunter.ui.others.mylist

import com.basar.moviehunter.base.BaseViewModel
import com.basar.moviehunter.domain.imagestorage.DownloadImageUseCase
import com.basar.moviehunter.domain.uimodel.MyListUI
import com.basar.moviehunter.extension.launch
import com.basar.moviehunter.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class MyListFragmentViewModel @Inject constructor(
    private val downloadImageUseCase: DownloadImageUseCase
) : BaseViewModel() {
    val savedMovieURL = SingleLiveEvent<List<MyListUI>>()
    val movieList = arrayListOf<MyListUI>()

    fun initVM() {
        downloadImage()
    }

    private fun downloadImage() = launch {
        downloadImageUseCase(null).flatMapMerge {
            downloadImageUseCase.decodeImages(it)
        }.onStart {
            showShimmer()
        }.onCompletion {
            hideShimmer()
        }.collect {
            savedMovieURL.postValue(it)
        }
    }
}