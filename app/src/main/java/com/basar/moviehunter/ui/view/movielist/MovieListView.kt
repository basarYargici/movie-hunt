package com.basar.moviehunter.ui.view.movielist

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.basar.moviehunter.data.model.MovieResponse
import com.basar.moviehunter.databinding.ViewMovieListBinding
import com.basar.moviehunter.domain.uimodel.MovieListUI

class MovieListView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val movieListAdapter = MovieListAdapter()

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
            movieListAdapter.submitList(item)
            with(movieListAdapter) {
                itemClickListener = onClickListener
                adapter = this
            }
            setHasFixedSize(true)
        }
    }
}