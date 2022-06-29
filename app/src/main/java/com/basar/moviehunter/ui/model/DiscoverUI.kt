package com.basar.moviehunter.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DiscoverUI(
    val id: Int? = null,
    val posterPath: String? = null,
) : Parcelable
