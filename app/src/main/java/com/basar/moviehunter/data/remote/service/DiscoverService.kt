package com.basar.moviehunter.data.remote.service

import com.basar.moviehunter.data.model.DiscoverMovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DiscoverService {
    @GET("discover/movie")
    suspend fun discoverMovie(
        @Query("page") page: Int?,
        @Query("region") region: String?
    ): DiscoverMovieResponse
}