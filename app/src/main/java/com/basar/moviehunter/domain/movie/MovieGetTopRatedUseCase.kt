package com.basar.moviehunter.domain.movie

import com.basar.moviehunter.data.model.TopRatedMoviesResponse
import com.basar.moviehunter.data.remote.repository.MovieRepository
import com.example.core.base.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieGetTopRatedUseCase @Inject constructor(
    val repository: MovieRepository
) : UseCase<Unit, TopRatedMoviesResponse>() {

    override fun execute(params: Unit): Flow<TopRatedMoviesResponse> {
        return repository.getTopRated()
    }
    // TODO: responseToUI
}