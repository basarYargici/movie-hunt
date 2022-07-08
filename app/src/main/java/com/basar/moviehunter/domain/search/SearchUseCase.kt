package com.basar.moviehunter.domain.search

import com.basar.moviehunter.data.model.SearchResponse
import com.basar.moviehunter.data.remote.repository.SearchRepository
import com.example.core.base.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    val repository: SearchRepository
) : UseCase<SearchUseCase.Params, SearchResponse>() {

    data class Params(
        var query: String
    )

    override fun execute(params: Params): Flow<SearchResponse> {
        return repository.multiSearch(params.query)
    }
    // TODO: responseToUI
}