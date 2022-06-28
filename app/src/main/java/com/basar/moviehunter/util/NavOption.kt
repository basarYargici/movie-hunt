package com.basar.moviehunter.util

import com.basar.moviehunter.R

enum class NavOption(val enter: Int, val exit: Int, val popEnter: Int, val popExit: Int) {
    ENTER_FROM_RIGHT(
        R.anim.slide_in_right,
        R.anim.slide_out_left,
        R.anim.slide_in_left,
        R.anim.slide_out_right
    ),
    ENTER_FROM_BOTTOM(
        R.anim.slide_in_bottom,
        R.anim.slide_out_top,
        R.anim.slide_in_top,
        R.anim.slide_out_bottom
    )
}