package com.basar.moviehunter.extension

import android.content.SharedPreferences

fun SharedPreferences.put(key: String, value: String) {
    edit().putString(key, value).apply()
}

fun SharedPreferences.putInt(key: String, value: Int) {
    edit().putInt(key, value).apply()
}