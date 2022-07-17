package com.basar.moviehunter.domain.uimodel

import android.os.Parcelable
import com.basar.moviehunter.data.model.ResultsItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchMovieUI(
    val results: List<ResultsItem?>? = null,
) : Parcelable