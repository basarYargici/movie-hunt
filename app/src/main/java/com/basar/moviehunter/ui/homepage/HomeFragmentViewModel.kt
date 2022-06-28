package com.basar.moviehunter.ui.homepage

import com.basar.moviehunter.base.BaseViewModel
import com.basar.moviehunter.domain.movie.MovieGetDetailUseCase
import com.basar.moviehunter.domain.movie.MovieGetPopularUseCase
import com.basar.moviehunter.domain.movie.MovieGetTopRatedUseCase
import com.basar.moviehunter.domain.movie.MovieGetUpcomingUseCase
import com.basar.moviehunter.extension.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val popularUseCase: MovieGetPopularUseCase,
    private val topUseCase: MovieGetTopRatedUseCase,
    private val detailUseCase: MovieGetDetailUseCase,
    private val upcomingUseCase: MovieGetUpcomingUseCase
) : BaseViewModel() {

    fun initVM() {
        getPopular()
    }

    private fun getPopular() = launch {
        popularUseCase(Unit).onStart {
            Timber.v("req started")
        }.onCompletion {
            Timber.v("req completed")
        }.collect {
            Timber.v(
                it.results?.forEach { movieResponse -> movieResponse?.id }.toString()
            )
        }
    }
}