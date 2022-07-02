package com.basar.moviehunter.domain.discover

import com.basar.moviehunter.data.model.DiscoverMovieResponse
import com.basar.moviehunter.data.model.Genre
import com.basar.moviehunter.data.remote.repository.DiscoverRepository
import com.basar.moviehunter.ui.model.DiscoverMovieUI
import com.basar.moviehunter.util.Mapper
import com.example.core.base.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DiscoverUseCase @Inject constructor(
    val repository: DiscoverRepository
) : UseCase<DiscoverUseCase.Params, DiscoverMovieUI>() {
    data class Params(
        val page: Int?,
        val region: String?
    )

    val genreList = listOf(
        Genre(28, "Action"),
        Genre(12, "Adventure"),
        Genre(16, "Animation"),
        Genre(35, "Comedy"),
        Genre(80, "Crime"),
        Genre(99, "Documentary"),
        Genre(18, "Drama"),
        Genre(10751, "Family"),
        Genre(14, "Fantasy"),
        Genre(36, "History"),
        Genre(27, "Horror"),
        Genre(10402, "Music"),
        Genre(9648, "Mystery"),
        Genre(10749, "Romance"),
        Genre(878, "Science_Fiction"),
        Genre(10770, "TV_Movie"),
        Genre(53, "Thriller"),
        Genre(10752, "War"),
        Genre(37, "Western"),
    )

    override fun execute(params: Params): Flow<DiscoverMovieUI> {
        return repository.discoverMovie(params.page, params.region).map(::response2UI)
    }

    private fun response2UI(response: DiscoverMovieResponse): DiscoverMovieUI {
        return object : Mapper<DiscoverMovieResponse, DiscoverMovieUI>() {
            override fun map(value: DiscoverMovieResponse): DiscoverMovieUI = with(value.results?.first()) {
                return DiscoverMovieUI(
                    id = this?.id,
                    posterPath = this?.posterPath,
                    categoryList = categoryMapper(this?.genreIds)
                )
            }
        }.map(response)
    }

    private fun categoryMapper(categoryList: List<Int?>?): List<String> {
        val convertedCategoryList = mutableListOf<String>()
        categoryList?.forEach { categoryId ->
            convertedCategoryList.add(genreList.first { genre ->
                genre.id == categoryId
            }.name)
        }
        return convertedCategoryList
    }
}