package com.basar.moviehunter.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import java.net.Inet4Address
import java.net.NetworkInterface

object AppInfoUtil {

    fun getIpAddress(): String? {
        try {
            val en = NetworkInterface.getNetworkInterfaces()
            while (en.hasMoreElements()) {
                val intf = en.nextElement()
                val enumIpAddr = intf.inetAddresses
                while (enumIpAddr.hasMoreElements()) {
                    val inetAddress = enumIpAddr.nextElement()
                    // for getting IPV4 format
                    if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                        return inetAddress.getHostAddress()?.toString()
                    }
                }
            }
        } catch (ignore: Exception) {
        }
        return ""
    }

    fun getDeviceModel() =
        "Android" + " | " + Build.MANUFACTURER + " | " + Build.MODEL + " | " + Build.VERSION.RELEASE

    fun getSoftwareVersion(context: Context): String {
        val pi: PackageInfo
        return try {
            pi = context.packageManager.getPackageInfo(context.packageName, 0)
            pi.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            "na"
        }
    }

    @SuppressLint("HardwareIds")
    fun getDeviceId(context: Context): String {
        return try {
            Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ANDROID_ID
            )
        } catch (e: Exception) {
            System.currentTimeMillis().toString()
        }
    }

    fun getApplicationChannel() = "Mobile"
}
