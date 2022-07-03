package com.basar.moviehunter.ui.homepage.moviedetail

import androidx.lifecycle.MutableLiveData
import com.basar.moviehunter.base.BaseViewModel
import com.basar.moviehunter.data.model.MovieDetailResponse
import com.basar.moviehunter.data.model.SimilarMoviesResponse
import com.basar.moviehunter.domain.movie.MovieGetDetailUseCase
import com.basar.moviehunter.domain.movie.MovieGetSimilarUseCase
import com.basar.moviehunter.extension.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val detailUseCase: MovieGetDetailUseCase,
    private val similarUseCase: MovieGetSimilarUseCase
) : BaseViewModel() {
    val movieDetail = MutableLiveData<MovieDetailResponse>()
    val similarMovies = MutableLiveData<SimilarMoviesResponse>()
    val isShimmerVisible = MutableLiveData(false)

    fun initVM(movieId: Int) = launch {
        detailUseCase(MovieGetDetailUseCase.Params(movieId))
            .zip(similarUseCase(MovieGetSimilarUseCase.Params(movieId)))
            { movieDetailResponse, similarMoviesResponse ->
                delay(5000L)
                movieDetail.postValue(movieDetailResponse)
                similarMovies.postValue(similarMoviesResponse)
            }.onStart {
                isShimmerVisible.postValue(true)
            }.onCompletion {
                isShimmerVisible.postValue(false)
            }.collect {}
    }

    fun getDetail(movieId: Int) = launch {
        detailUseCase(MovieGetDetailUseCase.Params(movieId)).onStart {
            delay(500)
            isShimmerVisible.postValue(true)
        }.onCompletion {
            isShimmerVisible.postValue(false)
        }.collect {
            movieDetail.postValue(it)
        }
    }

    fun getSimilar(movieId: Int) = launch {
        similarUseCase(MovieGetSimilarUseCase.Params(movieId)).onStart {
            isShimmerVisible.postValue(true)
        }.onCompletion {
            isShimmerVisible.postValue(false)
        }.collect {
            similarMovies.postValue(it)
        }
    }
}