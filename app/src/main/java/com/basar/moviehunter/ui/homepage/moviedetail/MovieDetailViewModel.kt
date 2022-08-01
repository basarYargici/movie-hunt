package com.basar.moviehunter.ui.homepage.moviedetail

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.basar.moviehunter.base.BaseViewModel
import com.basar.moviehunter.domain.imagestorage.UploadImageUseCase
import com.basar.moviehunter.domain.movie.MovieGetDetailUseCase
import com.basar.moviehunter.domain.movie.MovieGetSimilarUseCase
import com.basar.moviehunter.domain.uimodel.MovieDetailUI
import com.basar.moviehunter.domain.uimodel.RelatedMovieVideoUI
import com.basar.moviehunter.domain.uimodel.SimilarMovieUI
import com.basar.moviehunter.domain.video.GetRelatedMovieVideosUseCase
import com.basar.moviehunter.extension.launch
import com.basar.moviehunter.util.videoMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
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
        combine(getDetail(movieId),
            getSimilar(movieId),
            getMovieVideoPath(movieId)) { movieDetailUI: MovieDetailUI, similarMovieUI: SimilarMovieUI, relatedMovieVideoUI: RelatedMovieVideoUI ->
            movieDetail.postValue(movieDetailUI)
            similarMovies.postValue(similarMovieUI)
            youtubePath.postValue(videoMapper(relatedMovieVideoUI.results)?.key ?: "")
        }.onStart {
            showShimmer()
        }.onCompletion {
            hideShimmer()
        }.collect {
            println("Result $it")
        }
    }

    private fun getDetail(movieId: Int): Flow<MovieDetailUI> {
        return detailUseCase(MovieGetDetailUseCase.Params(movieId))
    }

    private fun getSimilar(movieId: Int): Flow<SimilarMovieUI> {
        return similarUseCase(MovieGetSimilarUseCase.Params(movieId))
    }

    private fun getMovieVideoPath(movieId: Int): Flow<RelatedMovieVideoUI> {
        return relatedVideosUseCase(GetRelatedMovieVideosUseCase.Params(movieId))
    }

    fun uploadImage(id: Int, uri: Uri) = launch {
        uploadImageUseCase(UploadImageUseCase.Params(id, uri)).collect {
            Timber.d(it.toString())
        }
    }
}