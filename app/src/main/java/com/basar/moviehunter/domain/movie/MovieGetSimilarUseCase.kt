package com.basar.moviehunter.domain.movie

import com.basar.moviehunter.data.model.SimilarMoviesResponse
import com.basar.moviehunter.data.remote.repository.MovieRepository
import com.basar.moviehunter.domain.uimodel.SimilarMovieUI
import com.basar.moviehunter.util.Mapper
import com.example.core.base.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieGetSimilarUseCase @Inject constructor(
    val repository: MovieRepository
) : UseCase<MovieGetSimilarUseCase.Params, SimilarMovieUI>() {

    data class Params(
        var movieId: Int
    )

    override fun execute(params: Params): Flow<SimilarMovieUI> {
        return repository.getSimilar(movieId = params.movieId).map(::response2UI)
    }

    private fun response2UI(response: SimilarMoviesResponse): SimilarMovieUI {
        return object : Mapper<SimilarMoviesResponse, SimilarMovieUI>() {
            override fun map(value: SimilarMoviesResponse): SimilarMovieUI {
                with(value) {
                    return SimilarMovieUI(
                        results
                    )
                }
            }
        }.map(response)
    }
}