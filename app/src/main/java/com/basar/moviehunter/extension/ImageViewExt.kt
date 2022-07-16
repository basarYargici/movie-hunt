package com.basar.moviehunter.extension

import android.widget.ImageView
import coil.load
import com.basar.moviehunter.BuildConfig

fun ImageView.setImageBitmap(url: String) {
    this.load(url)
}

fun getImageEndpoint(imagePath: String? = "error.jpg") = BuildConfig.IMAGE_BASE_URL + "t/p/w500" + imagePath