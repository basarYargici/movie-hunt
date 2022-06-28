package com.basar.moviehunter.util

import android.app.Application
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.basar.moviehunter.BaseApp
import javax.inject.Inject

class ResProvider @Inject constructor(val app: Application) {
    fun getString(@StringRes id: Int) = (app as BaseApp).getString(id)

    fun getString(@StringRes id: Int, vararg params: String) = String.format(getString(id), *params)

    fun getDrawable(@DrawableRes id: Int) = ContextCompat.getDrawable(app.applicationContext, id)

    fun getColor(@ColorRes id: Int) = ContextCompat.getColor(app.applicationContext, id)

    fun getColorStateList(@ColorRes id: Int) = ContextCompat.getColorStateList(app.applicationContext, id)

    fun getDimension(@DimenRes id: Int) = app.applicationContext.resources.getDimension(id)

}