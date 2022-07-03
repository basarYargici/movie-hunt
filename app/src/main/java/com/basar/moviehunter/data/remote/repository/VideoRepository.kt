package com.basar.moviehunter.data.remote.repository

import com.basar.moviehunter.base.BaseRepository
import com.basar.moviehunter.data.model.MovieVideosResponse
import com.basar.moviehunter.data.remote.service.MovieVideoService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface VideoRepository {
    fun getRelatedVideoList(movieId: Int): Flow<MovieVideosResponse>
}

class VideoRepositoryImpl @Inject constructor(
    private val service: MovieVideoService
) : BaseRepository(), VideoRepository {

    override fun getRelatedVideoList(movieId: Int): Flow<MovieVideosResponse> = sendRequest {
        service.getRelatedVideoList(movieId)
    }
}