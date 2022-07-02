package com.basar.moviehunter.ui.homepage.moviedetail

import androidx.lifecycle.MutableLiveData
import com.basar.moviehunter.base.BaseViewModel
import com.basar.moviehunter.data.model.MovieDetailResponse
import com.basar.moviehunter.domain.movie.MovieGetDetailUseCase
import com.basar.moviehunter.extension.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val detailUseCase: MovieGetDetailUseCase
) : BaseViewModel() {
    val movieDetail = MutableLiveData<MovieDetailResponse>()

    fun getDetail(movieId: Int) = launch {
        detailUseCase(MovieGetDetailUseCase.Params(movieId)).onStart {
            showLoading()
        }.onCompletion {
            hideLoading()
        }.collect {
            movieDetail.postValue(it)
        }
    }
}