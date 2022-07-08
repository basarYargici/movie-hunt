package com.basar.moviehunter.ui.search

import androidx.lifecycle.MutableLiveData
import com.basar.moviehunter.base.BaseViewModel
import com.basar.moviehunter.data.model.MediaType
import com.basar.moviehunter.data.model.MovieResponse
import com.basar.moviehunter.data.model.ResultsItem
import com.basar.moviehunter.domain.movie.MovieGetPopularUseCase
import com.basar.moviehunter.domain.search.SearchUseCase
import com.basar.moviehunter.domain.video.GetRelatedMovieVideosUseCase
import com.basar.moviehunter.extension.launch
import com.basar.moviehunter.ui.model.MovieListUI
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
    val youtubePath = MutableLiveData<String>()
    val isShimmerVisible = MutableLiveData(false)

    fun initVM() {
        getPopular()
    }

    fun getMultiSearch(query: String = "al") = launch {
        searchUseCase(SearchUseCase.Params(query)).onStart {
            isShimmerVisible.postValue(true)
        }.onCompletion {
            delay(500L)
            isShimmerVisible.postValue(false)
        }.collect {
            val movieResList = arrayListOf<MovieResponse>()
            it.results?.filterNotNull()?.filter { resultItem ->
                (resultItem.mediaType != MediaType.PERSON.type) && !resultItem.title.isNullOrBlank()
            }?.forEach { item ->
                movieResList.add(
                    resultItemToMovieResponse(item)
                )
            }
            searchListUI.postValue(
                MovieListUI(
                    title = "Search Results",
                    movieList = movieResList
                )
            )
        }
    }

    private fun resultItemToMovieResponse(item: ResultsItem) = MovieResponse(
        item.overview,
        item.originalLanguage,
        item.originalTitle,
        item.video,
        item.title,
        item.genreIds,
        item.posterPath,
        item.backdropPath,
        item.releaseDate,
        item.popularity,
        item.voteAverage,
        item.id,
        item.adult,
        item.voteCount
    )

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