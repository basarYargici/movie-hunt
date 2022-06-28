package com.basar.moviehunter.data.remote.service

import com.basar.moviehunter.data.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("search/multi")
    suspend fun multiSearch(@Query("query") query: String): SearchResponse
}