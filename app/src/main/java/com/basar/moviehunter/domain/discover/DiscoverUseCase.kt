package com.basar.moviehunter.domain.discover

import com.basar.moviehunter.data.model.DiscoverMovieResponse
import com.basar.moviehunter.data.remote.repository.DiscoverRepository
import com.basar.moviehunter.ui.model.DiscoverMovieUI
import com.basar.moviehunter.util.Mapper
import com.basar.moviehunter.util.categoryMapper
import com.example.core.base.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DiscoverUseCase @Inject constructor(
    val repository: DiscoverRepository
) : UseCase<DiscoverUseCase.Params, DiscoverMovieUI>() {
    data class Params(
        val page: Int?,
        val region: String?
    )

    override fun execute(params: Params): Flow<DiscoverMovieUI> {
        return repository.discoverMovie(params.page, params.region).map(::response2UI)
    }

    private fun response2UI(response: DiscoverMovieResponse): DiscoverMovieUI {
        return object : Mapper<DiscoverMovieResponse, DiscoverMovieUI>() {
            override fun map(value: DiscoverMovieResponse): DiscoverMovieUI = with(value.results?.random()) {
                return DiscoverMovieUI(
                    id = this?.id,
                    posterPath = this?.posterPath,
                    youtubePath = this?.backdropPath,
                    categoryList = categoryMapper(this?.genreIds)
                )
            }
        }.map(response)
    }
}