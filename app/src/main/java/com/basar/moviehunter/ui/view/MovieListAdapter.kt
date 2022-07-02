package com.basar.moviehunter.ui.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.basar.moviehunter.data.model.MovieResponse
import com.basar.moviehunter.databinding.ItemMovieListBinding
import com.basar.moviehunter.extension.getImageEndpoint
import com.basar.moviehunter.extension.setImageBitmap
import com.basar.moviehunter.ui.model.MovieListUI

class MovieListAdapter(private var movieListUI: MovieListUI) :
    RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder>() {

    var itemClickListener: ((MovieResponse?) -> Unit)? = null

    inner class MovieListViewHolder(private val binding: ItemMovieListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val movie = movieListUI.movieList?.get(position)
            binding.apply {
                imageView.setImageBitmap(getImageEndpoint(movie?.posterPath))
                materialCV.setOnClickListener {
                    itemClickListener?.invoke(movie)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        return MovieListViewHolder(
            ItemMovieListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) = holder.bind(position)

    override fun getItemCount() = movieListUI.movieList?.size ?: 0
}