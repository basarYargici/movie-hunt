package com.basar.moviehunter.data.remote.service

import com.basar.moviehunter.data.model.MovieVideosResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieVideoService {

    @GET("movie/{movie_id}/videos")
    suspend fun getRelatedVideoList(@Path("movie_id") movieId: Int): MovieVideosResponse
}