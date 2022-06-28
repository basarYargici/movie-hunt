package com.basar.moviehunter.data.remote.service

import com.basar.moviehunter.data.model.DiscoverMovieResponse
import retrofit2.http.Body
import retrofit2.http.GET

interface DiscoverService {
    @GET("discover/movie")
    suspend fun discoverMovie(@Body query: String) : DiscoverMovieResponse

}