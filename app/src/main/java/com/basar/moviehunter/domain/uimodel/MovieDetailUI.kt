package com.basar.moviehunter.domain.uimodel

import android.os.Parcelable
import com.basar.moviehunter.data.model.GenresItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetailUI(
    val id: Int? = null,
    val genres: List<GenresItem?>? = null,
    val voteAverage: Double? = null,
    val releaseDate: String? = null,
    val overview: String? = null,
    val posterPath: String? = null,
) : Parcelable