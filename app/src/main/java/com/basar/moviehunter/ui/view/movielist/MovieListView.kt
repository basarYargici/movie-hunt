package com.basar.moviehunter.ui.view.movielist

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.basar.moviehunter.data.model.MovieResponse
import com.basar.moviehunter.databinding.ViewMovieListBinding
import com.basar.moviehunter.domain.uimodel.MovieListUI
import com.google.android.material.card.MaterialCardView

class MovieListView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {
    private val binding: ViewMovieListBinding =
        ViewMovieListBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )

    fun setItem(item: MovieListUI, onClickListener: ((MovieResponse?) -> Unit)? = null) {
        with(binding) {
            tvTitle.text = item.title
            initRV(item.movieList, onClickListener)
        }
    }

    private fun ViewMovieListBinding.initRV(item: List<MovieResponse>?, onClickListener: ((MovieResponse?) -> Unit)?) {
        with(rvItems) {
            with(MovieListAdapter(item)) {
                itemClickListener = onClickListener
                adapter = this
            }
//            addItemDecoration()
            setHasFixedSize(true)
        }
    }
}