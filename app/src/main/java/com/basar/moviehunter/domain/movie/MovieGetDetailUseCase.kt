package com.basar.moviehunter.domain.movie

import com.basar.moviehunter.data.model.MovieDetailResponse
import com.basar.moviehunter.data.remote.repository.MovieRepository
import com.example.core.base.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieGetDetailUseCase @Inject constructor(
    val repository: MovieRepository
) : UseCase<MovieGetDetailUseCase.Params, MovieDetailResponse>() {

    data class Params(
        var movieId: Int
    )

    override fun execute(params: Params): Flow<MovieDetailResponse> {
        return repository.getDetail(movieId = params.movieId)
    }
    // TODO: responseToUI
}