package com.basar.moviehunter.ui.search

import androidx.lifecycle.MutableLiveData
import com.basar.moviehunter.base.BaseViewModel
import com.basar.moviehunter.data.model.MediaType
import com.basar.moviehunter.data.model.MovieResponse
import com.basar.moviehunter.domain.movie.MovieGetPopularUseCase
import com.basar.moviehunter.domain.search.SearchUseCase
import com.basar.moviehunter.domain.uimodel.MovieListUI
import com.basar.moviehunter.domain.video.GetRelatedMovieVideosUseCase
import com.basar.moviehunter.extension.launch
import com.basar.moviehunter.util.SingleLiveEvent
import com.basar.moviehunter.util.resultItemToMovieResponseMapper
import com.basar.moviehunter.util.videoMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class SearchFragmentViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase,
    private val popularUseCase: MovieGetPopularUseCase,
    private val relatedVideosUseCase: GetRelatedMovieVideosUseCase
) : BaseViewModel() {
    val popularMovieListUI = MutableLiveData<MovieListUI>()
    val searchListUI = MutableLiveData<MovieListUI>()
    val youtubePath = SingleLiveEvent<String>()
    val isNotFoundAnimVisible = MutableLiveData(false)
    val isInitialized = MutableLiveData(false)

    fun initVM() {
        isInitialized.postValue(true)
        getPopular()
    }

    fun getMultiSearch(query: String = "al") = launch {
        searchUseCase(SearchUseCase.Params(query)).onStart {
            showShimmer()
        }.onCompletion {
            delay(500L)
            hideShimmer()
        }.collect {
            val movieResList = arrayListOf<MovieResponse>()
            it.results?.filterNotNull()?.filter { resultItem ->
                (resultItem.mediaType != MediaType.PERSON.type) && !resultItem.title.isNullOrBlank()
            }?.forEach { item ->
                movieResList.add(
                    resultItemToMovieResponseMapper(item)
                )
            }
            searchListUI.postValue(
                MovieListUI(
                    title = "Search Results",
                    movieList = movieResList
                )
            )
            when (movieResList.size) {
                0 -> isNotFoundAnimVisible.postValue(true)
                else -> isNotFoundAnimVisible.postValue(false)
            }
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
        }.collect { videoResults ->
            youtubePath.postValue(videoMapper(videoResults.results)?.key ?: "")
        }
    }
}