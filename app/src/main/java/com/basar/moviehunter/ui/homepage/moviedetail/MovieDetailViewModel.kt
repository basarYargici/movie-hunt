package com.basar.moviehunter.ui.homepage.moviedetail

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.basar.moviehunter.base.BaseViewModel
import com.basar.moviehunter.domain.imagestorage.UploadImageUseCase
import com.basar.moviehunter.domain.movie.MovieGetDetailUseCase
import com.basar.moviehunter.domain.movie.MovieGetSimilarUseCase
import com.basar.moviehunter.domain.uimodel.MovieDetailUI
import com.basar.moviehunter.domain.uimodel.SimilarMovieUI
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
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val detailUseCase: MovieGetDetailUseCase,
    private val similarUseCase: MovieGetSimilarUseCase,
    private val relatedVideosUseCase: GetRelatedMovieVideosUseCase,
    private val uploadImageUseCase: UploadImageUseCase
) : BaseViewModel() {
    val movieDetail = MutableLiveData<MovieDetailUI>()
    val similarMovies = MutableLiveData<SimilarMovieUI>()
    val youtubePath = MutableLiveData<String>()

    fun initVM(movieId: Int) = launch {
        listOf(
            async { getDetail(movieId) },
            async { getSimilar(movieId) },
            async { getMovieVideoPath(movieId) }
        ).awaitAll().asFlow().onStart {
            showShimmer()
        }.onCompletion {
            delay(500L)
            hideShimmer()
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

    private fun getMovieVideoPath(movieId: Int) = launch {
        relatedVideosUseCase(GetRelatedMovieVideosUseCase.Params(movieId)).collect { videoResults ->
            youtubePath.postValue(videoMapper(videoResults.results)?.key ?: "")
        }
    }

    fun uploadImage(id: Int, uri: Uri) = launch {
        uploadImageUseCase(UploadImageUseCase.Params(id, uri)).collect {
            Timber.d(it.toString())
        }
    }
}