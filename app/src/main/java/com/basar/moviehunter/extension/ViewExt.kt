package com.basar.moviehunter.extension

import android.view.View

fun View.visibleIf(condition: Boolean) {
    visibility = if (condition) View.VISIBLE else View.GONE
}

fun View.setVisible(){
    visibility = View.VISIBLE
}

fun View.setGone(){
    visibility = View.GONE
}