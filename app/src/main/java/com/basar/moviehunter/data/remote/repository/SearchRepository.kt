package com.basar.moviehunter.data.remote.repository

import com.basar.moviehunter.base.BaseRepository
import com.basar.moviehunter.data.model.SearchResponse
import com.basar.moviehunter.data.remote.service.SearchService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface SearchRepository {
    fun multiSearch(query: String): Flow<SearchResponse>
}

class SearchRepositoryImpl @Inject constructor(
    private val service: SearchService
) : BaseRepository(), SearchRepository {

    override fun multiSearch(query: String): Flow<SearchResponse> = sendRequest {
        service.multiSearch(query)
    }
}