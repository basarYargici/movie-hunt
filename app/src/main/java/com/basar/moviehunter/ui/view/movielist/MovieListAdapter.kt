package com.basar.moviehunter.ui.view.movielist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.basar.moviehunter.data.model.MovieResponse
import com.basar.moviehunter.databinding.ItemMovieListBinding
import com.basar.moviehunter.extension.getImageEndpoint
import com.basar.moviehunter.extension.setImageBitmap

class MovieListAdapter : ListAdapter<MovieResponse, MovieListAdapter.MovieListViewHolder>(DiffCallback()) {
    var itemClickListener: ((MovieResponse?) -> Unit)? = null

    private class DiffCallback : DiffUtil.ItemCallback<MovieResponse>() {
        override fun areItemsTheSame(oldItem: MovieResponse, newItem: MovieResponse) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MovieResponse, newItem: MovieResponse) =
            oldItem == newItem
    }

    inner class MovieListViewHolder(private val binding: ItemMovieListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val movie = getItem(position)
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

    override fun getItemCount() = currentList.size
}