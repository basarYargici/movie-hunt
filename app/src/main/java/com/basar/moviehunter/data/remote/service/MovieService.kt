package com.basar.moviehunter.data.remote.service

import com.basar.moviehunter.data.model.MovieDetailResponse
import com.basar.moviehunter.data.model.PopularMoviesResponse
import com.basar.moviehunter.data.model.TopRatedMoviesResponse
import com.basar.moviehunter.data.model.UpcomingMovieResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("movie/popular")
    suspend fun getPopular(): PopularMoviesResponse

    @GET("movie/top_rated")
    suspend fun getTopRated(): TopRatedMoviesResponse

    @GET("movie/{movie_id}")
    suspend fun getDetail(@Path("movie_id") movieId: Int): MovieDetailResponse

    // TODO: region should be enum
    @GET("movie/upcoming")
    suspend fun getUpcoming(@Query("region") region: String?): UpcomingMovieResponse
}