package com.basar.moviehunter.domain.movie

import com.basar.moviehunter.data.model.PopularMoviesResponse
import com.basar.moviehunter.data.remote.repository.MovieRepository
import com.example.core.base.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieGetPopularUseCase @Inject constructor(
    val repository: MovieRepository
) : UseCase<Unit, PopularMoviesResponse>() {

    override fun execute(params: Unit): Flow<PopularMoviesResponse> {
        return repository.getPopular()
    }
    // TODO: responseToUI
}