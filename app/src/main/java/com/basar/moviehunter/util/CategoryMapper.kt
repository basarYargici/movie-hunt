package com.basar.moviehunter.util

import com.basar.moviehunter.data.model.Genre
import com.basar.moviehunter.data.model.MovieVideoModel
import com.basar.moviehunter.data.model.MovieVideosResponse

// TODO: clean the code
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

fun categoryMapper(categoryList: List<Int?>?): List<String> {
    val convertedCategoryList = mutableListOf<String>()
    categoryList?.forEach { categoryId ->
        convertedCategoryList.add(genreList.first { genre ->
            genre.id == categoryId
        }.name)
    }
    return convertedCategoryList
}

fun videoMapper(video: MovieVideosResponse): MovieVideoModel? =
    video.results?.filter(predicate = { movieVideoModel ->
        movieVideoModel?.site == "YouTube"
    })?.random()