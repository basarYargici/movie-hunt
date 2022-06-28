package com.basar.moviehunter.domain.movie

import com.basar.moviehunter.data.model.UpcomingMovieResponse
import com.basar.moviehunter.data.remote.repository.MovieRepository
import com.example.core.base.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieGetUpcomingUseCase @Inject constructor(
    val repository: MovieRepository
) : UseCase<MovieGetUpcomingUseCase.Params, UpcomingMovieResponse>() {

    data class Params(
        var region: String
    )

    override fun execute(params: Params): Flow<UpcomingMovieResponse> {
        return repository.getUpcoming(region = params.region)
    }
    // TODO: responseToUI
}