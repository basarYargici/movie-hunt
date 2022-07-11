package com.basar.moviehunter.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UpcomingMovieUI(
    val id: Int? = null,
    val backdropPath: String? = null,
    val releaseDate: String? = null,
    val title: String? = null,
    val overview: String? = null,
    val categoryList: List<String>? = null,
) : Parcelable
