package com.basar.moviehunter.domain.movie

import com.basar.moviehunter.data.model.TopRatedMoviesResponse
import com.basar.moviehunter.data.remote.repository.MovieRepository
import com.basar.moviehunter.domain.uimodel.TopRatedMovieUI
import com.basar.moviehunter.util.Mapper
import com.example.core.base.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieGetTopRatedUseCase @Inject constructor(
    val repository: MovieRepository
) : UseCase<Unit, TopRatedMovieUI>() {

    override fun execute(params: Unit): Flow<TopRatedMovieUI> {
        return repository.getTopRated().map(::response2UI)
    }

    private fun response2UI(response: TopRatedMoviesResponse): TopRatedMovieUI {
        return object : Mapper<TopRatedMoviesResponse, TopRatedMovieUI>() {
            override fun map(value: TopRatedMoviesResponse): TopRatedMovieUI {
                with(value) {
                    return TopRatedMovieUI(
                        results
                    )
                }
            }
        }.map(response)
    }
}