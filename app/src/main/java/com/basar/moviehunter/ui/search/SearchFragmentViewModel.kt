package com.basar.moviehunter.ui.search

import androidx.lifecycle.MutableLiveData
import com.basar.moviehunter.base.BaseViewModel
import com.basar.moviehunter.data.model.MovieResponse
import com.basar.moviehunter.domain.movie.MovieGetPopularUseCase
import com.basar.moviehunter.domain.search.SearchUseCase
import com.basar.moviehunter.domain.video.GetRelatedMovieVideosUseCase
import com.basar.moviehunter.extension.launch
import com.basar.moviehunter.ui.model.MovieListUI
import com.basar.moviehunter.util.videoMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchFragmentViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase,
    private val popularUseCase: MovieGetPopularUseCase,
    private val relatedVideosUseCase: GetRelatedMovieVideosUseCase
) : BaseViewModel() {
    val popularMovieListUI = MutableLiveData<MovieListUI>()
    val youtubePath = MutableLiveData<String>()

    fun initVM() {
        getMultiSearch()
        getPopular()
    }

    private fun getMultiSearch(query: String = "al") = launch {
        searchUseCase(SearchUseCase.Params(query)).onStart {
            Timber.v("req started")
        }.onCompletion {
            Timber.v("req completed")
        }.collect {
            Timber.v(
                "getMultiSearch : "
                        + it.results?.forEach { searchResponse -> searchResponse?.name }.toString()
            )
        }
    }

    private fun getPopular() = launch {
        popularUseCase(Unit).onStart {
            showLoading()
        }.onCompletion {
            hideLoading()
        }.collect {
            val movieList: ArrayList<MovieResponse> = it.results?.filterNotNull() as ArrayList<MovieResponse>
            popularMovieListUI.postValue(
                MovieListUI(
                    title = "Popular Movies",
                    movieList = movieList
                )
            )
        }
    }

    fun getMovieVideoPath(movieId: Int) = launch {
        relatedVideosUseCase(GetRelatedMovieVideosUseCase.Params(movieId)).onStart {
            showLoading()
        }.onCompletion {
            hideLoading()
        }.collect {
            youtubePath.postValue(videoMapper(it)?.key ?: "")
        }
    }

}