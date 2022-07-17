package com.basar.moviehunter.domain.movie

import com.basar.moviehunter.data.model.PopularMoviesResponse
import com.basar.moviehunter.data.remote.repository.MovieRepository
import com.basar.moviehunter.domain.uimodel.PopularMoviesUI
import com.basar.moviehunter.util.Mapper
import com.example.core.base.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieGetPopularUseCase @Inject constructor(
    val repository: MovieRepository
) : UseCase<Unit, PopularMoviesUI>() {

    override fun execute(params: Unit): Flow<PopularMoviesUI> {
        return repository.getPopular().map(::response2UI)
    }

    private fun response2UI(response: PopularMoviesResponse): PopularMoviesUI {
        return object : Mapper<PopularMoviesResponse, PopularMoviesUI>() {
            override fun map(value: PopularMoviesResponse): PopularMoviesUI {
                with(value) {
                    return PopularMoviesUI(
                        results
                    )
                }
            }
        }.map(response)
    }
}