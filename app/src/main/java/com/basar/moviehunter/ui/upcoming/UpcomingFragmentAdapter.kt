package com.basar.moviehunter.ui.upcoming

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.basar.moviehunter.databinding.ItemUpcomigMoviesBinding
import com.basar.moviehunter.domain.uimodel.UpcomingMovieUI
import com.basar.moviehunter.extension.getImageEndpoint
import com.basar.moviehunter.extension.setImageBitmap
import com.basar.moviehunter.ui.upcoming.UpcomingFragmentAdapter.UpcomingMoviesViewHolder

class UpcomingFragmentAdapter : ListAdapter<UpcomingMovieUI, UpcomingMoviesViewHolder>(DiffCallback()) {
    var shareClickListener: ((UpcomingMovieUI?) -> Unit)? = null
    var playClickListener: ((UpcomingMovieUI?) -> Unit)? = null

    private class DiffCallback : DiffUtil.ItemCallback<UpcomingMovieUI>() {
        override fun areItemsTheSame(oldItem: UpcomingMovieUI, newItem: UpcomingMovieUI) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: UpcomingMovieUI, newItem: UpcomingMovieUI) =
            oldItem == newItem
    }

    inner class UpcomingMoviesViewHolder(private val binding: ItemUpcomigMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val movie = getItem(position)
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

    override fun onBindViewHolder(holder: UpcomingMoviesViewHolder, position: Int) = holder.bind(position)

    override fun getItemCount() = currentList.size
}