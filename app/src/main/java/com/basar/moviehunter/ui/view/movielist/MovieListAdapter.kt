package com.basar.moviehunter.ui.view.movielist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.basar.moviehunter.data.model.MovieResponse
import com.basar.moviehunter.databinding.ItemMovieListBinding
import com.basar.moviehunter.extension.getImageEndpoint
import com.basar.moviehunter.extension.setImageBitmap

class MovieListAdapter(private var movieList: List<MovieResponse>?) :
    RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder>() {

    var itemClickListener: ((MovieResponse?) -> Unit)? = null

    inner class MovieListViewHolder(private val binding: ItemMovieListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val movie = movieList?.get(position)
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

    override fun getItemCount() = movieList?.size ?: 0
}