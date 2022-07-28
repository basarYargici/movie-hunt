package com.basar.moviehunter.ui.view.discover

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.basar.moviehunter.databinding.ViewDiscoverMovieBinding
import com.basar.moviehunter.domain.uimodel.DiscoverMovieUI
import com.basar.moviehunter.extension.getImageEndpoint
import com.basar.moviehunter.extension.setImageBitmap
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
        val vote = "${item.voteAverage} rate / ${item.voteCount} votes"
        with(binding) {
            tvTitle.text = item.title
            tvCategories.text = item.categoryList?.take(3)?.joinToString(" - ")
            tvReleaseDate.text = item.releaseDate.toString()
            tvVote.text = vote
            imageView.setImageBitmap(getImageEndpoint(item.backdropPath))
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