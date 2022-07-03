package com.basar.moviehunter.data.remote.repository

import com.basar.moviehunter.base.BaseRepository
import com.basar.moviehunter.data.model.DiscoverMovieResponse
import com.basar.moviehunter.data.remote.service.DiscoverService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface DiscoverRepository {
    fun discoverMovie(
        page: Int?,
        region: String?
    ): Flow<DiscoverMovieResponse>
}

class DiscoverRepositoryImpl @Inject constructor(
    private val service: DiscoverService
) : BaseRepository(), DiscoverRepository {
    override fun discoverMovie(page: Int?, region: String?): Flow<DiscoverMovieResponse> = sendRequest {
        service.discoverMovie(page, region)
    }
}