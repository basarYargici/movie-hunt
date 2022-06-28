package com.basar.moviehunter.di

import com.basar.moviehunter.di.qualifier.AppId
import com.basar.moviehunter.di.qualifier.AppVersion
import com.basar.moviehunter.di.qualifier.AuthKey
import com.basar.moviehunter.di.qualifier.DeviceId
import com.basar.moviehunter.util.model.AppConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppConfigModule {

    @Singleton
    @Provides
    fun provideAppConfig(
        @DeviceId deviceId: String,
        @AppVersion appVersion: String,
        @AppId appId: String,
        @AuthKey authKey: String,
    ) = AppConfig(
        applicationId = appId,
        version = appVersion,
        authKey = authKey,
        deviceId = deviceId,
    )
}
