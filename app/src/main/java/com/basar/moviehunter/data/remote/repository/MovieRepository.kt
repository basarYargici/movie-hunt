package com.basar.moviehunter.data.remote.repository

import com.basar.moviehunter.base.BaseRepository
import com.basar.moviehunter.data.model.MovieDetailResponse
import com.basar.moviehunter.data.model.PopularMoviesResponse
import com.basar.moviehunter.data.model.TopRatedMoviesResponse
import com.basar.moviehunter.data.model.UpcomingMovieResponse
import com.basar.moviehunter.data.remote.service.MovieService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface MovieRepository {
    fun getPopular(): Flow<PopularMoviesResponse>
    fun getTopRated(): Flow<TopRatedMoviesResponse>
    fun getDetail(movieId: Int): Flow<MovieDetailResponse>
    fun getUpcoming(region: String?): Flow<UpcomingMovieResponse>
}

class MovieRepositoryImpl @Inject constructor(
    private val service: MovieService
) : BaseRepository(), MovieRepository {
    override fun getPopular(): Flow<PopularMoviesResponse> = sendRequest {
        service.getPopular()
    }

    override fun getTopRated(): Flow<TopRatedMoviesResponse> = sendRequest {
        service.getTopRated()
    }

    override fun getDetail(movieId: Int): Flow<MovieDetailResponse> = sendRequest {
        service.getDetail(movieId)
    }

    override fun getUpcoming(region: String?): Flow<UpcomingMovieResponse> = sendRequest {
        service.getUpcoming(region)
    }
}