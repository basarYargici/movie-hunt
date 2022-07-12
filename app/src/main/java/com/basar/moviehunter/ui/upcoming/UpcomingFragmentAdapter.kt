package com.basar.moviehunter.ui.upcoming

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.basar.moviehunter.databinding.ItemUpcomigMoviesBinding
import com.basar.moviehunter.extension.getImageEndpoint
import com.basar.moviehunter.extension.setImageBitmap
import com.basar.moviehunter.ui.model.UpcomingMovieUI
import com.basar.moviehunter.ui.upcoming.UpcomingFragmentAdapter.UpcomingMoviesViewHolder

class UpcomingFragmentAdapter(var movieList: List<UpcomingMovieUI>?) :
    RecyclerView.Adapter<UpcomingMoviesViewHolder>() {

    var shareClickListener: ((UpcomingMovieUI?) -> Unit)? = null
    var playClickListener: ((UpcomingMovieUI?) -> Unit)? = null

    inner class UpcomingMoviesViewHolder(private val binding: ItemUpcomigMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val movie = movieList?.get(position)
            binding.apply {
                imgPoster.setImageBitmap(getImageEndpoint(movie?.posterPath))
                tvOverview.text = movie?.overview
                tvCategories.text = movie?.categoryList?.joinToString(separator = " - ")
                imgShareButton.setOnClickListener {
                    shareClickListener?.invoke(movie)
                }
                imgPlayButton.setOnClickListener {
                    playClickListener?.invoke(movie)
                }
                imgPoster.setOnClickListener {
                    playClickListener?.invoke(movie)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingMoviesViewHolder {
        return UpcomingMoviesViewHolder(
            ItemUpcomigMoviesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = movieList?.size ?: 0
    override fun onBindViewHolder(holder: UpcomingMoviesViewHolder, position: Int) = holder.bind(position)
}