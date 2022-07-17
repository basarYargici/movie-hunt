package com.basar.moviehunter.di

import android.app.Application
import android.content.Context
import com.basar.moviehunter.BuildConfig
import com.basar.moviehunter.di.qualifier.*
import com.basar.moviehunter.util.AppInfoUtil
import com.basar.moviehunter.util.ResProvider
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
    fun provideResProvider(
        app: Application
    ) = ResProvider(app)
}
