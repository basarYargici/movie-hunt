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
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val popularUseCase: MovieGetPopularUseCase,
    private val topRatedUseCase: MovieGetTopRatedUseCase,
    private val upcomingUseCase: MovieGetUpcomingUseCase,
    private val discoverUseCase: DiscoverUseCase,
    private val relatedVideosUseCase: GetRelatedMovieVideosUseCase
) : BaseViewModel() {

    val discoverUIModel = MutableLiveData<DiscoverMovieUI>()
    val popularMovieListUI = MutableLiveData<MovieListUI>()
    val topRatedMovieListUI = MutableLiveData<MovieListUI>()
    val isShimmerVisible = MutableLiveData(false)


    fun initVM() = launch {
        val a = async { getPopular() }
        val b = async { getTopRated() }
        val c = async { getDiscovery() }
        awaitAll(a, b, c).asFlow().onStart {
            isShimmerVisible.postValue(true)
            delay(2000L)
        }.onCompletion {
            isShimmerVisible.postValue(false)
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
        topRatedUseCase(Unit).onStart {
            Timber.v("req started")
        }.onCompletion {
            Timber.v("req completed")
        }.collect {
            val movieList: ArrayList<MovieResponse> = it.results?.filterNotNull() as ArrayList<MovieResponse>
            topRatedMovieListUI.postValue(
                MovieListUI(
                    title = "Top Rated Movies",
                    movieList = movieList
                )
            )
        }
    }

    private fun getUpcoming(region: String = "TR") = launch {
        upcomingUseCase(MovieGetUpcomingUseCase.Params(region)).onStart {
            Timber.v("req started")
        }.onCompletion {
            Timber.v("req completed")
        }.collect {
            Timber.v(
                "getUpcoming : " + it.results?.forEach { movieResponse -> movieResponse?.id }.toString()
            )
        }
    }

    private fun getDiscovery(page: Int? = 1, region: String? = "TR") = launch {
        val discoverReq = async { discoverUseCase(DiscoverUseCase.Params(page, region)) }
        showLoading()
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

//        discoverUseCase(DiscoverUseCase.Params(page, region))
//            .onStart {
//                showLoading()
//            }.onCompletion {
//                hideLoading()
//            }.collect {
//                discoverUIModel.postValue(it)
//            }
    }


}