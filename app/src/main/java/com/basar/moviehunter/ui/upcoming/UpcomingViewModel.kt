package com.basar.moviehunter.ui.upcoming

import androidx.lifecycle.MutableLiveData
import com.basar.moviehunter.base.BaseViewModel
import com.basar.moviehunter.domain.movie.MovieGetUpcomingUseCase
import com.basar.moviehunter.domain.video.GetRelatedMovieVideosUseCase
import com.basar.moviehunter.extension.launch
import com.basar.moviehunter.ui.model.UpcomingMovieUI
import com.basar.moviehunter.util.videoMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class UpcomingViewModel @Inject constructor(
    private val upcomingUseCase: MovieGetUpcomingUseCase,
    private val relatedVideosUseCase: GetRelatedMovieVideosUseCase
) : BaseViewModel() {
    var upcomingMoviesUI = MutableLiveData<List<UpcomingMovieUI>>()
    var youtubePath = MutableLiveData<String>()
    fun initVM() {
        getUpcoming()
    }

    private fun getUpcoming(region: String = "TR") = launch {
        upcomingUseCase(MovieGetUpcomingUseCase.Params(region)).onStart {
            showShimmer()
        }.onCompletion {
            delay(500L)
            hideShimmer()
        }.collect {
            upcomingMoviesUI.postValue(it)
        }
    }

    fun getTutorialLink(id: Int = 0) = launch {
        relatedVideosUseCase(GetRelatedMovieVideosUseCase.Params(id))
            .onCompletion {

            }.collect { movieResponse ->
                youtubePath.postValue(videoMapper(movieResponse)?.key ?: "")
            }
    }

}