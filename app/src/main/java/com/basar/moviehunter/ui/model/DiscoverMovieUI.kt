package com.basar.moviehunter.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DiscoverMovieUI(
    val id: Int? = null,
    val title: String? = null,
    val posterPath: String? = null,
    val backdropPath: String? = null,
    val releaseDate: String? = null,
    val youtubePath: String? = null,
    val categoryList: List<String>? = null,
    val voteAverage: Double? = null,
    val voteCount: Int? = null
) : Parcelable