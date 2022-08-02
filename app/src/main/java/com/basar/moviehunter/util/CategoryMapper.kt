package com.basar.moviehunter.util

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import com.basar.moviehunter.data.model.Genre
import com.basar.moviehunter.data.model.MovieResponse
import com.basar.moviehunter.data.model.MovieVideoModel
import com.basar.moviehunter.data.model.ResultsItem
import java.io.ByteArrayOutputStream

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
    Genre(878, "Science Fiction"),
    Genre(10770, "TV Movie"),
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

fun videoMapper(videoList: List<MovieVideoModel?>?): MovieVideoModel? =
    videoList?.filter(predicate = { movieVideoModel ->
        movieVideoModel?.site == "YouTube"
    })?.random()

// TODO refactor
fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
    val bytes = ByteArrayOutputStream()
    inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
    val path = MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
    return Uri.parse(path)
}

fun resultItemToMovieResponseMapper(item: ResultsItem) = with(item) {
    MovieResponse(
        overview,
        originalLanguage,
        originalTitle,
        video,
        title,
        genreIds,
        posterPath,
        backdropPath,
        releaseDate,
        popularity,
        voteAverage,
        id,
        adult,
        voteCount
    )
}