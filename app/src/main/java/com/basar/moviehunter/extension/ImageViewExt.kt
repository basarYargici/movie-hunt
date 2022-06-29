package com.basar.moviehunter.extension

import android.widget.ImageView
import coil.load

fun ImageView.setImageBitmap(url: String) {
    this.load(url)
}