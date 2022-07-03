package com.basar.moviehunter.domain.movie

import com.basar.moviehunter.data.model.SimilarMoviesResponse
import com.basar.moviehunter.data.remote.repository.MovieRepository
import com.example.core.base.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieGetSimilarUseCase @Inject constructor(
    val repository: MovieRepository
) : UseCase<MovieGetSimilarUseCase.Params, SimilarMoviesResponse>() {

    data class Params(
        var movieId: Int
    )

    override fun execute(params: Params): Flow<SimilarMoviesResponse> {
        return repository.getSimilar(movieId = params.movieId)
    }
    // TODO: responseToUI
}