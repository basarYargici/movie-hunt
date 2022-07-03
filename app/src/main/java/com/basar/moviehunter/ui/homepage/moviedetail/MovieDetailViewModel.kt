package com.basar.moviehunter.ui.homepage.moviedetail

import androidx.lifecycle.MutableLiveData
import com.basar.moviehunter.base.BaseViewModel
import com.basar.moviehunter.data.model.MovieDetailResponse
import com.basar.moviehunter.data.model.SimilarMoviesResponse
import com.basar.moviehunter.domain.movie.MovieGetDetailUseCase
import com.basar.moviehunter.domain.movie.MovieGetSimilarUseCase
import com.basar.moviehunter.domain.video.GetRelatedMovieVideosUseCase
import com.basar.moviehunter.extension.launch
import com.basar.moviehunter.util.videoMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val detailUseCase: MovieGetDetailUseCase,
    private val similarUseCase: MovieGetSimilarUseCase,
    private val relatedVideosUseCase: GetRelatedMovieVideosUseCase
) : BaseViewModel() {
    val movieDetail = MutableLiveData<MovieDetailResponse>()
    val similarMovies = MutableLiveData<SimilarMoviesResponse>()
    val youtubePath = MutableLiveData<String>()
    val isShimmerVisible = MutableLiveData(false)

    fun initVM(movieId: Int) = launch {
        listOf(
            async { getDetail(movieId) },
            async { getSimilar(movieId) },
            async { getMovie(movieId) }
        ).awaitAll().asFlow().onStart {
            isShimmerVisible.postValue(true)
        }.onCompletion {
            delay(500L)
            isShimmerVisible.postValue(false)
        }.collect {}
    }

    private fun getDetail(movieId: Int) = launch {
        detailUseCase(MovieGetDetailUseCase.Params(movieId)).collect {
            movieDetail.postValue(it)
        }
    }

    private fun getSimilar(movieId: Int) = launch {
        similarUseCase(MovieGetSimilarUseCase.Params(movieId)).collect {
            similarMovies.postValue(it)
        }
    }

    private fun getMovie(movieId: Int) = launch {
        relatedVideosUseCase(GetRelatedMovieVideosUseCase.Params(movieId)).collect {
            youtubePath.postValue(videoMapper(it)?.key ?: "")
        }
    }
}