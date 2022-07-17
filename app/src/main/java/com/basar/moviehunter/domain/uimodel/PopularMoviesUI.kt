package com.basar.moviehunter.domain.uimodel

import android.os.Parcelable
import com.basar.moviehunter.data.model.MovieResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class PopularMoviesUI(
    val results: List<MovieResponse?>? = null,
) : Parcelable