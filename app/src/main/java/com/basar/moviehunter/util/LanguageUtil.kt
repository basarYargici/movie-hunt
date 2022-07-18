package com.basar.moviehunter.util

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import com.basar.moviehunter.base.app.AppPreferencesImpl.Companion.APP_PREF_NAME
import com.basar.moviehunter.base.app.AppPreferencesImpl.Companion.KEY_DEFAULT_LANG
import com.basar.moviehunter.base.app.AppPreferencesImpl.Companion.KEY_LANG
import java.util.*

object LanguageUtil {
    fun updateBaseContextLocale(context: Context): Context {
        val locale = Locale(getLanguage(context))
        Locale.setDefault(locale)

        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> updateResources(context, locale)
            else -> updateResourcesLegacy(context, locale)
        }
    }

    private fun getLanguage(context: Context): String {
        val preferences = context.getSharedPreferences(APP_PREF_NAME, Context.MODE_PRIVATE)
        return preferences.getString(KEY_LANG, KEY_DEFAULT_LANG) ?: KEY_DEFAULT_LANG
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResources(context: Context, locale: Locale): Context {
        val resources = context.resources
        val configuration = resources.configuration
        configuration.apply {
            setLocale(locale)
            setLayoutDirection(locale)
            return context.createConfigurationContext(this)
        }
    }

    @Suppress("DEPRECATION")
    private fun updateResourcesLegacy(context: Context, locale: Locale): Context {
        val resources = context.resources
        val configuration = resources.configuration
        configuration.apply {
            this.locale = locale
            setLayoutDirection(locale)
            resources.updateConfiguration(this, resources.displayMetrics)

            return context
        }
    }
}