package com.basar.moviehunter.domain.uimodel

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MyListUI(
    val id: String? = null,
    val image: Bitmap? = null,
) : Parcelable