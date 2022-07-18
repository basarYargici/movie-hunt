package com.basar.moviehunter

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.basar.moviehunter.base.app.AppRepository
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class BaseApp : Application() {
    @Inject
    lateinit var appRepository: AppRepository

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        setTheme()
    }

    private fun setTheme() {
        AppCompatDelegate.setDefaultNightMode(appRepository.getDarkModeState())
    }
}