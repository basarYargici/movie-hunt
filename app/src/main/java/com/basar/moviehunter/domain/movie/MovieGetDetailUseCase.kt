package com.basar.moviehunter.domain.movie

import com.basar.moviehunter.data.model.MovieDetailResponse
import com.basar.moviehunter.data.remote.repository.MovieRepository
import com.basar.moviehunter.domain.uimodel.MovieDetailUI
import com.basar.moviehunter.util.Mapper
import com.example.core.base.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieGetDetailUseCase @Inject constructor(
    val repository: MovieRepository
) : UseCase<MovieGetDetailUseCase.Params, MovieDetailUI>() {

    data class Params(
        var movieId: Int
    )

    override fun execute(params: Params): Flow<MovieDetailUI> {
        return repository.getDetail(movieId = params.movieId).map(::response2UI)
    }

    private fun response2UI(response: MovieDetailResponse): MovieDetailUI {
        return object : Mapper<MovieDetailResponse, MovieDetailUI>() {
            override fun map(value: MovieDetailResponse): MovieDetailUI {
                with(value) {
                    return MovieDetailUI(
                        id, genres, voteAverage, releaseDate, overview, posterPath
                    )
                }
            }
        }.map(response)
    }
}