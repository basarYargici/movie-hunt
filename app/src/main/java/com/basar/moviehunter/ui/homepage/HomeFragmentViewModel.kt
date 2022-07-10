package com.basar.moviehunter.ui.homepage

import androidx.lifecycle.MutableLiveData
import com.basar.moviehunter.base.BaseViewModel
import com.basar.moviehunter.data.model.MovieResponse
import com.basar.moviehunter.domain.discover.DiscoverUseCase
import com.basar.moviehunter.domain.movie.MovieGetPopularUseCase
import com.basar.moviehunter.domain.movie.MovieGetTopRatedUseCase
import com.basar.moviehunter.domain.movie.MovieGetUpcomingUseCase
import com.basar.moviehunter.domain.video.GetRelatedMovieVideosUseCase
import com.basar.moviehunter.extension.launch
import com.basar.moviehunter.ui.model.DiscoverMovieUI
import com.basar.moviehunter.ui.model.MovieListUI
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
    private val relatedVideosUseCase: GetRelatedMovieVideosUseCase
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
                    title = "Popular Movies",
                    movieList = movieList
                )
            )
        }
    }

    private fun getTopRated() = launch {
        topRatedUseCase(Unit).collect {
            topRatedMovieListUI.postValue(
                MovieListUI(
                    title = "Top Rated Movies",
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
                }.collect { video ->
                    discoverUIModel.postValue(
                        DiscoverMovieUI(
                            discover.id,
                            discover.posterPath,
                            youtubePath = videoMapper(video)?.key,
                            categoryList = discover.categoryList
                        )
                    )
                }
        }
    }
}