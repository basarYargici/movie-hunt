package com.basar.moviehunter.extension

import android.widget.ImageView
import coil.load
import com.basar.moviehunter.BuildConfig
import com.basar.moviehunter.R

fun ImageView.setImageBitmap(url: String) {
    this.load(url) {
        placeholder(R.drawable.ic_download)
    }
}

fun getImageEndpoint(imagePath: String? = "error.jpg") = BuildConfig.IMAGE_BASE_URL + "t/p/w500" + imagePath