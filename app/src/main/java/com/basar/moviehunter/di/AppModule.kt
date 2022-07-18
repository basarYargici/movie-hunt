package com.basar.moviehunter.di

import android.app.Application
import android.content.Context
import com.basar.moviehunter.BuildConfig
import com.basar.moviehunter.base.app.AppPreferences
import com.basar.moviehunter.base.app.AppPreferencesImpl
import com.basar.moviehunter.base.app.AppRepository
import com.basar.moviehunter.base.app.AppRepositoryImpl
import com.basar.moviehunter.di.qualifier.*
import com.basar.moviehunter.util.AppInfoUtil
import com.basar.moviehunter.util.ResProvider
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @DeviceId
    @Provides
    fun provideDeviceId(
        @ApplicationContext context: Context
    ) = AppInfoUtil.getDeviceId(context)

    @AppVersion
    @Provides
    fun provideAppVersion(@ApplicationContext context: Context) = AppInfoUtil.getSoftwareVersion(context)

    @ApiUrl
    @Provides
    fun provideApiUrl() = BuildConfig.BASE_URL

    @AppId
    @Provides
    fun provideAppId() = BuildConfig.APPLICATION_ID

    @AuthKey
    @Provides
    fun provideAuthKey() = BuildConfig.API_KEY

    @Provides
    @Singleton
    fun provideResProvider(app: Application) = ResProvider(app)

    @Provides
    @Singleton
    fun provideAppPreferences(
        @ApplicationContext context: Context, gson: Gson
    ): AppPreferences = AppPreferencesImpl(context, gson)

    @Provides
    @Singleton
    fun provideAppRepository(
        appPreferences: AppPreferences
    ): AppRepository = AppRepositoryImpl(appPreferences)
}
