package com.basar.moviehunter.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.basar.moviehunter.data.model.MovieResponse
import com.basar.moviehunter.databinding.ItemMostSearchedMoviesBinding
import com.basar.moviehunter.extension.getImageEndpoint
import com.basar.moviehunter.extension.setImageBitmap
import com.basar.moviehunter.ui.search.SearchFragmentAdapter.SearchItemsViewHolder

class SearchFragmentAdapter : ListAdapter<MovieResponse, SearchItemsViewHolder>(DiffCallback()) {

    var itemClickListener: ((MovieResponse?) -> Unit)? = null
    var onPlayClickListener: ((MovieResponse?) -> Unit)? = null

    private class DiffCallback : DiffUtil.ItemCallback<MovieResponse>() {
        override fun areItemsTheSame(oldItem: MovieResponse, newItem: MovieResponse) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MovieResponse, newItem: MovieResponse) =
            oldItem == newItem
    }

    inner class SearchItemsViewHolder(private val binding: ItemMostSearchedMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val movie = getItem(position)
            binding.apply {
                imgPoster.setImageBitmap(getImageEndpoint(movie?.posterPath))
                tvTitle.text = movie?.title
                ll.setOnClickListener {
                    itemClickListener?.invoke(movie)
                }
                imgPlayButton.setOnClickListener {
                    onPlayClickListener?.invoke(movie)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemsViewHolder {
        return SearchItemsViewHolder(
            ItemMostSearchedMoviesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SearchItemsViewHolder, position: Int) = holder.bind(position)

    override fun getItemCount() = currentList.size
}