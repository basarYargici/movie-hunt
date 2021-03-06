package com.basar.moviehunter.base.app

import javax.inject.Inject

interface AppRepository {
    fun setReversedLanguage()
    fun getLanguage(): String
    fun getReverseLanguage(): String
    fun setLastVersion(version: String)
    fun getLastVersion(): String
    fun setDarkModeState(state: Int)
    fun getDarkModeState(): Int
}

class AppRepositoryImpl @Inject constructor(
    private val appPreferences: AppPreferences
) : AppRepository {
    companion object {
        const val TURKISH = "tr"
        const val ENGLISH = "en"
    }

    override fun setReversedLanguage() = appPreferences.setLanguage(getReverseLanguage())
    override fun getLanguage(): String = appPreferences.getLanguage()
    override fun getReverseLanguage(): String = if (getLanguage() == TURKISH) ENGLISH else TURKISH
    override fun setLastVersion(version: String) = appPreferences.setLastVersion(version)
    override fun getLastVersion(): String = appPreferences.getLastVersion()
    override fun setDarkModeState(state: Int) = appPreferences.setDarkModeState(state)
    override fun getDarkModeState(): Int = appPreferences.getDarkModeState()
}