package com.basar.moviehunter.domain.discover

import com.basar.moviehunter.data.model.DiscoverMovieResponse
import com.basar.moviehunter.data.remote.repository.DiscoverRepository
import com.basar.moviehunter.ui.model.DiscoverUI
import com.basar.moviehunter.util.Mapper
import com.example.core.base.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DiscoverUseCase @Inject constructor(
    val repository: DiscoverRepository
) : UseCase<DiscoverUseCase.Params, DiscoverUI>() {
    data class Params(
        val page: Int?,
        val region: String?
    )

    override fun execute(params: Params): Flow<DiscoverUI> {
        return repository.discoverMovie(params.page, params.region).map(::response2UI)
    }

    private fun response2UI(response: DiscoverMovieResponse): DiscoverUI {
        return object : Mapper<DiscoverMovieResponse, DiscoverUI>() {
            override fun map(value: DiscoverMovieResponse): DiscoverUI = with(value.results?.first()) {
                return DiscoverUI(id = this?.id, posterPath = this?.posterPath)
            }
        }.map(response)
    }
}