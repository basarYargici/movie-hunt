package com.basar.moviehunter.base.app

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.basar.moviehunter.extension.put
import com.basar.moviehunter.extension.putInt
import com.google.gson.Gson
import javax.inject.Inject


interface AppPreferences {
    fun setLanguage(language: String)
    fun getLanguage(): String
    fun setLastVersion(version: String)
    fun getLastVersion(): String
    fun setDarkModeState(state: Int)
    fun getDarkModeState(): Int
}

class AppPreferencesImpl @Inject constructor(context: Context, val gson: Gson) : AppPreferences {

    companion object {
        const val APP_PREF_NAME = "app_pref"
        const val KEY_LANG = "lang"
        const val KEY_DEFAULT_LANG = "tr"
        const val KEY_LAST_VERSION = "last_version"
        const val DARK_MODE_STATE = "dark_mode_state"
    }

    private val preferences by lazy { context.getSharedPreferences(APP_PREF_NAME, Context.MODE_PRIVATE) }

    override fun setLanguage(language: String) = preferences.put(KEY_LANG, language)
    override fun getLanguage(): String = preferences.getString(KEY_LANG, KEY_DEFAULT_LANG) ?: ""

    override fun setLastVersion(version: String) = preferences.put(KEY_LAST_VERSION, version)
    override fun getLastVersion(): String = preferences.getString(KEY_LAST_VERSION, "") ?: ""

    override fun setDarkModeState(state: Int) = preferences.putInt(DARK_MODE_STATE, state)
    override fun getDarkModeState(): Int =
        preferences.getInt(DARK_MODE_STATE, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

}