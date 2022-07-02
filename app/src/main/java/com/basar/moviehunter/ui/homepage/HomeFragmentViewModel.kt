package com.basar.moviehunter.ui.homepage

import androidx.lifecycle.MutableLiveData
import com.basar.moviehunter.BuildConfig
import com.basar.moviehunter.base.BaseViewModel
import com.basar.moviehunter.domain.discover.DiscoverUseCase
import com.basar.moviehunter.domain.movie.MovieGetDetailUseCase
import com.basar.moviehunter.domain.movie.MovieGetPopularUseCase
import com.basar.moviehunter.domain.movie.MovieGetTopRatedUseCase
import com.basar.moviehunter.domain.movie.MovieGetUpcomingUseCase
import com.basar.moviehunter.extension.launch
import com.basar.moviehunter.ui.model.DiscoverUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val popularUseCase: MovieGetPopularUseCase,
    private val topRatedUseCase: MovieGetTopRatedUseCase,
    private val detailUseCase: MovieGetDetailUseCase,
    private val upcomingUseCase: MovieGetUpcomingUseCase,
    private val discoverUseCase: DiscoverUseCase,
) : BaseViewModel() {

    val discoverUIModel = MutableLiveData<DiscoverUI>()
    val discoverImageUrl = MutableLiveData("")

    fun initVM() {
        // TODO: requests
//        getPopular()
//        getTopRated()
//        getDetail(372058)
//        getUpcoming()
        getDiscovery()
    }

    private fun getPopular() = launch {
        popularUseCase(Unit).onStart {
            Timber.v("req started")
        }.onCompletion {
            Timber.v("req completed")
        }.collect {
            Timber.v(
                "getPopular : " + it.results?.forEach { movieResponse -> movieResponse?.id }.toString()
            )
        }
    }

    private fun getTopRated() = launch {
        topRatedUseCase(Unit).onStart {
            Timber.v("req started")
        }.onCompletion {
            Timber.v("req completed")
        }.collect {
            Timber.v(
                "getTopRated : " + it.results?.forEach { movieResponse -> movieResponse?.id }.toString()
            )
        }
    }

    private fun getDetail(movieId: Int) = launch {
        detailUseCase(MovieGetDetailUseCase.Params(movieId)).onStart {
            Timber.v("req started")
        }.onCompletion {
            Timber.v("req completed")
        }.collect {
            Timber.v("getDetail : " + it.title)
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
        discoverUseCase(DiscoverUseCase.Params(page, region)).onStart {
            Timber.v("req started")
            showLoading()
        }.onCompletion {
            Timber.v("req completed")
            hideLoading()
        }.collect {
            discoverUIModel.postValue(it)
            getImagePath(it.posterPath)
            Timber.v(
                "getDiscovery : " + it.posterPath.toString()
            )
        }
    }

    private fun getImagePath(imagePath: String? = "error.jpg") {
        discoverImageUrl.postValue(BuildConfig.IMAGE_BASE_URL + "t/p/original/" + imagePath)
    }
}