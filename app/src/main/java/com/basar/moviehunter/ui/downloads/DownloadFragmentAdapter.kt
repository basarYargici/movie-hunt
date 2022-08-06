package com.basar.moviehunter.ui.downloads

import android.media.MediaMetadataRetriever
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.basar.moviehunter.R
import com.basar.moviehunter.data.model.DownloadedMovie
import com.basar.moviehunter.databinding.ItemMostSearchedMoviesBinding
import com.basar.moviehunter.ui.downloads.DownloadFragmentAdapter.DownloadItemsViewHolder
import java.io.File

class DownloadFragmentAdapter : ListAdapter<DownloadedMovie, DownloadItemsViewHolder>(DiffCallback()) {
    var itemClickListener: ((DownloadedMovie?) -> Unit)? = null

    private class DiffCallback : DiffUtil.ItemCallback<DownloadedMovie>() {
        override fun areItemsTheSame(oldItem: DownloadedMovie, newItem: DownloadedMovie) =
            oldItem.path == newItem.path

        override fun areContentsTheSame(oldItem: DownloadedMovie, newItem: DownloadedMovie) =
            oldItem == newItem
    }

    inner class DownloadItemsViewHolder(private val binding: ItemMostSearchedMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val media = MediaMetadataRetriever()

        fun bind(position: Int) {
            val downloadedMovie = getItem(position)
            binding.apply {
                downloadedMovie?.let { movie ->
                    media.setDataSource(movie.path)
                    val img = media.getFrameAtTime(1000)
                    if (img != null) {
                        imgPoster.setImageBitmap(img)
                    } else {
                        imgPoster.setImageResource(R.drawable.ic_error)
                    }

                    tvTitle.text = File(movie.path).nameWithoutExtension
                    ll.setOnClickListener {
                        itemClickListener?.invoke(movie)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DownloadItemsViewHolder {
        return DownloadItemsViewHolder(
            ItemMostSearchedMoviesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DownloadItemsViewHolder, position: Int) = holder.bind(position)

    override fun getItemCount() = currentList.size
}