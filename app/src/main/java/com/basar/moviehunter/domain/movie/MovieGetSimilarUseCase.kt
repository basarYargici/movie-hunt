package com.basar.moviehunter.domain.movie

import com.basar.moviehunter.data.model.MovieDetailResponse
import com.basar.moviehunter.data.model.PopularMoviesResponse
import com.basar.moviehunter.data.remote.repository.MovieRepository
import com.example.core.base.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieGetSimilarUseCase @Inject constructor(
    val repository: MovieRepository
) : UseCase<MovieGetSimilarUseCase.Params, PopularMoviesResponse>() {

    data class Params(
        var movieId: Int
    )

    override fun execute(params: Params): Flow<PopularMoviesResponse> {
        return repository.getSimilar(movieId = params.movieId)
    }
    // TODO: responseToUI
}