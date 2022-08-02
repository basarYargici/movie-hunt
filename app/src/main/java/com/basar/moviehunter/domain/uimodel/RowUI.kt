package com.basar.moviehunter.domain.uimodel

import androidx.annotation.DrawableRes

sealed class RowUI {
    data class HeaderRowUI(val text: String, val style: HeaderTextStyle) : RowUI()
    data class TextRowUI(
        @DrawableRes val iconRes: Int? = null,
        val text: String
    ) : RowUI()

    data class SwitchRowUI(
        @DrawableRes val iconRes: Int? = null,
        val text: String,
        var isChecked: Boolean? = false,
    ) : RowUI()
}

enum class HeaderTextStyle {
    SMALL, BIG
}