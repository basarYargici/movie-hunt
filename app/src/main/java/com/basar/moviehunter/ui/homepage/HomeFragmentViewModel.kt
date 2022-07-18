package com.basar.moviehunter.ui.homepage

import androidx.lifecycle.MutableLiveData
import com.basar.moviehunter.R
import com.basar.moviehunter.base.BaseViewModel
import com.basar.moviehunter.data.model.MovieResponse
import com.basar.moviehunter.domain.discover.DiscoverUseCase
import com.basar.moviehunter.domain.movie.MovieGetPopularUseCase
import com.basar.moviehunter.domain.movie.MovieGetTopRatedUseCase
import com.basar.moviehunter.domain.uimodel.DiscoverMovieUI
import com.basar.moviehunter.domain.uimodel.MovieListUI
import com.basar.moviehunter.domain.video.GetRelatedMovieVideosUseCase
import com.basar.moviehunter.extension.launch
import com.basar.moviehunter.util.ResProvider
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
class HomeFragmentViewModel @Inject constructor(
    private val popularUseCase: MovieGetPopularUseCase,
    private val topRatedUseCase: MovieGetTopRatedUseCase,
    private val discoverUseCase: DiscoverUseCase,
    private val relatedVideosUseCase: GetRelatedMovieVideosUseCase,
    val resProvider: ResProvider
) : BaseViewModel() {

    val discoverUIModel = MutableLiveData<DiscoverMovieUI>()
    val popularMovieListUI = MutableLiveData<MovieListUI>()
    val topRatedMovieListUI = MutableLiveData<MovieListUI>()

    fun initVM() = launch {
        listOf(
            async { getPopular() },
            async { getTopRated() },
            async { getDiscovery() }
        ).awaitAll().asFlow().onStart {
            showShimmer()
        }.onCompletion {
            delay(500L)
            hideShimmer()
        }.collect {}
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
                    title = resProvider.getString(R.string.popular_movies),
                    movieList = movieList
                )
            )
        }
    }

    private fun getTopRated() = launch {
        topRatedUseCase(Unit).collect {
            topRatedMovieListUI.postValue(
                MovieListUI(
                    title = resProvider.getString(R.string.top_rated_movies),
                    movieList = it.results?.filterNotNull() as ArrayList<MovieResponse>
                )
            )
        }
    }

    private fun getDiscovery(page: Int? = 1, region: String? = "TR") = launch {
        val discoverReq = async { discoverUseCase(DiscoverUseCase.Params(page, region)) }
        discoverReq.await().collect { discover ->
            relatedVideosUseCase(GetRelatedMovieVideosUseCase.Params(discover.id ?: 0))
                .onCompletion {
                    hideLoading()
                }.collect { videoResults ->
                    discoverUIModel.postValue(
                        with(discover) {
                            DiscoverMovieUI(
                                id = id,
                                title = title,
                                posterPath = posterPath,
                                backdropPath = backdropPath,
                                releaseDate = releaseDate,
                                voteAverage = voteAverage,
                                voteCount = voteCount,
                                youtubePath = videoMapper(videoResults.results)?.key,
                                categoryList = categoryList
                            )
                        }
                    )
                }
        }
    }
}