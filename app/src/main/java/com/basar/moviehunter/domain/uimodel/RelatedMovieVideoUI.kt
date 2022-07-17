package com.basar.moviehunter.domain.uimodel

import android.os.Parcelable
import com.basar.moviehunter.data.model.MovieVideoModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class RelatedMovieVideoUI(
    val results: List<MovieVideoModel?>? = null
) : Parcelable