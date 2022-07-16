package com.basar.moviehunter.ui.others.mylist

import androidx.lifecycle.MutableLiveData
import com.basar.moviehunter.base.BaseViewModel
import com.basar.moviehunter.domain.imagestorage.DownloadImageUseCase
import com.basar.moviehunter.domain.uimodel.MyListUI
import com.basar.moviehunter.extension.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyListFragmentViewModel @Inject constructor(
    private val downloadImageUseCase: DownloadImageUseCase
) : BaseViewModel() {
    val savedMovieURL = MutableLiveData<List<MyListUI>>()

    fun downloadImage() = launch {
        // TODO: look how it collects list even list is empty :)
        downloadImageUseCase(null).collect {
            savedMovieURL.postValue(it)
        }
    }
}