package com.basar.moviehunter.domain.uimodel

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class AboutUI(
    val toolbarTitle: String? = null,
    @DrawableRes val toolbarRes: Int? = null,
    val welcomeTitle: String? = null,
    val description: String? = null,
    val technologyListTitle: String? = null,
    val technologyList: ArrayList<TechnologyModel>? = null,
    val connectTitle: String? = null,
    val connectActionList: ArrayList<ConnectionButtonModel>? = null
) : Parcelable

@Parcelize
data class TechnologyModel(
    @DrawableRes val imageRes: Int? = null,
    val title: String? = null,
) : Parcelable

@Parcelize
data class ConnectionButtonModel(
    val title: String? = null,
    val actionUrl: String? = null,
) : Parcelable

