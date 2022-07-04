package com.basar.moviehunter.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.basar.moviehunter.data.model.MovieResponse
import com.basar.moviehunter.databinding.ItemMostSearchedMoviesBinding
import com.basar.moviehunter.extension.getImageEndpoint
import com.basar.moviehunter.extension.setImageBitmap

class SearchFragmentAdapter(private var movieList: List<MovieResponse>?) :
    RecyclerView.Adapter<SearchFragmentAdapter.SearchItemsViewHolder>() {

    var itemClickListener: ((MovieResponse?) -> Unit)? = null
    var onPlayClickListener: ((MovieResponse?) -> Unit)? = null

    inner class SearchItemsViewHolder(private val binding: ItemMostSearchedMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val movie = movieList?.get(position)
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

    override fun getItemCount() = movieList?.size ?: 0

}