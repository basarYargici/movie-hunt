package com.basar.moviehunter.domain.discover

import com.basar.moviehunter.data.model.DiscoverMovieResponse
import com.basar.moviehunter.data.remote.repository.DiscoverRepository
import com.example.core.base.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DiscoverUseCase @Inject constructor(
    val repository: DiscoverRepository
) : UseCase<DiscoverUseCase.Params, DiscoverMovieResponse>() {
    data class Params(
        val page: Int?,
        val region: String?
    )

    override fun execute(params: Params): Flow<DiscoverMovieResponse> {
        return repository.discoverMovie(params.page, params.region)
    }
}