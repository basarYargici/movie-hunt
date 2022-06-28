package com.basar.moviehunter.data.remote.service

import retrofit2.http.Body
import retrofit2.http.GET

interface SearchService {

    @GET("search/multi")
    suspend fun multiSearch(@Body query: String)
}