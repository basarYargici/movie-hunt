package com.basar.moviehunter.domain.uimodel

import androidx.annotation.DrawableRes

sealed class RowUI {
    data class HeaderRowUI(val text: String, val style: HeaderTextStyle) : RowUI()
    data class TextRowUI(
        val id: String? = null,
        @DrawableRes val iconRes: Int? = null,
        val text: String
    ) : RowUI()
}

enum class HeaderTextStyle {
    SMALL, BIG
}