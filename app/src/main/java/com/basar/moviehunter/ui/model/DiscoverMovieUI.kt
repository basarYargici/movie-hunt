package com.basar.moviehunter.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DiscoverMovieUI(
    val id: Int? = null,
    val posterPath: String? = null,
    val youtubePath: String? = null,
    val categoryList: List<String>? = null,
) : Parcelable