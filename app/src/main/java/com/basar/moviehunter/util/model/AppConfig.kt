package com.basar.moviehunter.util.model

import com.basar.moviehunter.util.AppInfoUtil

data class AppConfig(
    val applicationId: String,
    val version: String,
    val authKey: String,
    val deviceId: String,
    val channel: String = AppInfoUtil.getApplicationChannel(),
    val deviceModel: String = AppInfoUtil.getDeviceModel(),
)
