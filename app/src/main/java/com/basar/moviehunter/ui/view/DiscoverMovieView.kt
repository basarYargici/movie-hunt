package com.basar.moviehunter.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.basar.moviehunter.databinding.ViewDiscoverMovieBinding
import com.basar.moviehunter.extension.getImageEndpoint
import com.basar.moviehunter.extension.setImageBitmap
import com.basar.moviehunter.ui.model.DiscoverMovieUI
import com.google.android.material.card.MaterialCardView

class DiscoverMovieView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {
    private val binding: ViewDiscoverMovieBinding =
        ViewDiscoverMovieBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )

    fun setItem(
        item: DiscoverMovieUI,
        onInfoClickListener: (() -> Unit)? = null,
        onAddToListClickListener: (() -> Unit)? = null,
        onPlayClickListener: (() -> Unit)? = null
    ) {
        with(binding) {
            var categoriesStr = ""
            item.categoryList?.forEach { s -> categoriesStr += s }
            tvCategories.text = categoriesStr
            imageView.setImageBitmap(getImageEndpoint(item.posterPath))
            btnInfo.setOnClickListener {
                onInfoClickListener?.invoke()
            }
            imageView.setOnClickListener {
                onInfoClickListener?.invoke()
            }
            btnPlay.setOnClickListener {
                onPlayClickListener?.invoke()
            }
            btnAddToList.setOnClickListener {
                onAddToListClickListener?.invoke()
            }
        }
    }
}