package com.basar.moviehunter.ui.downloads

import android.media.MediaMetadataRetriever
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.basar.moviehunter.R
import com.basar.moviehunter.data.model.DownloadedMovie
import com.basar.moviehunter.databinding.ItemMostSearchedMoviesBinding
import java.io.File

class DownloadFragmentAdapter : RecyclerView.Adapter<DownloadFragmentAdapter.DownloadItemsViewHolder>() {
    var itemClickListener: ((DownloadedMovie?) -> Unit)? = null
    var movieList: List<DownloadedMovie>? = null

    inner class DownloadItemsViewHolder(private val binding: ItemMostSearchedMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val media = MediaMetadataRetriever()

        fun bind(position: Int) {
            val downloadedMovie = movieList?.get(position)
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

    override fun getItemCount() = movieList?.size ?: 0
}