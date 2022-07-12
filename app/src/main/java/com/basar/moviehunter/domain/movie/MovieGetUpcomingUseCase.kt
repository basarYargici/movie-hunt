package com.basar.moviehunter.domain.movie

import com.basar.moviehunter.data.model.UpcomingMovieResponse
import com.basar.moviehunter.data.remote.repository.MovieRepository
import com.basar.moviehunter.ui.model.UpcomingMovieUI
import com.basar.moviehunter.util.Mapper
import com.basar.moviehunter.util.categoryMapper
import com.example.core.base.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieGetUpcomingUseCase @Inject constructor(
    val repository: MovieRepository
) : UseCase<MovieGetUpcomingUseCase.Params, List<UpcomingMovieUI>>() {

    data class Params(
        var region: String
    )

    override fun execute(params: Params): Flow<List<UpcomingMovieUI>> {
        return repository.getUpcoming(region = params.region).map(::response2UI)
    }

    private fun response2UI(response: UpcomingMovieResponse): List<UpcomingMovieUI> {
        return object : Mapper<UpcomingMovieResponse, List<UpcomingMovieUI>>() {
            val upcomingMovieList = arrayListOf<UpcomingMovieUI>()
            override fun map(value: UpcomingMovieResponse): List<UpcomingMovieUI> {
                value.results?.filterNotNull()?.forEach { movieResponse ->
                    upcomingMovieList.add(
                        UpcomingMovieUI(
                            id = movieResponse.id,
                            posterPath = movieResponse.posterPath,
                            releaseDate = movieResponse.releaseDate,
                            title = movieResponse.title,
                            overview = movieResponse.overview,
                            categoryList = categoryMapper(movieResponse.genreIds)
                        )
                    )
                }
                return upcomingMovieList
            }
        }.map(response)
    }
}