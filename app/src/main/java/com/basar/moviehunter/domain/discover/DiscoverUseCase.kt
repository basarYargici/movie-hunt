package com.basar.moviehunter.domain.discover

import com.basar.moviehunter.data.model.DiscoverMovieResponse
import com.basar.moviehunter.data.model.MovieResponse
import com.basar.moviehunter.data.remote.repository.DiscoverRepository
import com.basar.moviehunter.util.Mapper
import com.example.core.base.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DiscoverUseCase @Inject constructor(
    val repository: DiscoverRepository
) : UseCase<DiscoverUseCase.Params, MovieResponse>() {
    data class Params(
        val page: Int?,
        val region: String?
    )

    override fun execute(params: Params): Flow<MovieResponse> {
        return repository.discoverMovie(params.page, params.region).map(::response2UI)
    }

    private fun response2UI(response: DiscoverMovieResponse): MovieResponse {
        return object : Mapper<DiscoverMovieResponse, MovieResponse>() {
            override fun map(value: DiscoverMovieResponse): MovieResponse {
                val movieResponse: MovieResponse? = value.results?.first()
                return movieResponse ?: MovieResponse()
            }
        }.map(response)
    }
}