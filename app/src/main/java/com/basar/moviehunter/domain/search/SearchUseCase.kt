package com.basar.moviehunter.domain.search

import com.basar.moviehunter.data.model.SearchResponse
import com.basar.moviehunter.data.remote.repository.SearchRepository
import com.basar.moviehunter.domain.uimodel.SearchMovieUI
import com.basar.moviehunter.util.Mapper
import com.example.core.base.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    val repository: SearchRepository
) : UseCase<SearchUseCase.Params, SearchMovieUI>() {

    data class Params(
        var query: String
    )

    override fun execute(params: Params): Flow<SearchMovieUI> {
        return repository.multiSearch(params.query).map(::response2UI)
    }

    private fun response2UI(response: SearchResponse): SearchMovieUI {
        return object : Mapper<SearchResponse, SearchMovieUI>() {
            override fun map(value: SearchResponse): SearchMovieUI {
                with(value) {
                    return SearchMovieUI(
                        results
                    )
                }
            }
        }.map(response)
    }
}