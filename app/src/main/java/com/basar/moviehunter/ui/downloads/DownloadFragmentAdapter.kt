package com.basar.moviehunter.ui.downloads

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.basar.moviehunter.data.model.DownloadedMovie
import com.basar.moviehunter.databinding.ItemMostSearchedMoviesBinding
import com.basar.moviehunter.extension.setImageBitmap
import java.io.File

class DownloadFragmentAdapter(private var movieList: List<DownloadedMovie>?) :
    RecyclerView.Adapter<DownloadFragmentAdapter.DownloadItemsViewHolder>() {

    var itemClickListener: ((DownloadedMovie?) -> Unit)? = null
    var onPlayClickListener: ((DownloadedMovie?) -> Unit)? = null

    inner class DownloadItemsViewHolder(private val binding: ItemMostSearchedMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val downloadedMovie = movieList?.get(position)
            binding.apply {
                downloadedMovie?.let { movie ->
                    imgPoster.setImageBitmap(movie.path)
                    tvTitle.text = File(movie.path).name
                    ll.setOnClickListener {
                        itemClickListener?.invoke(movie)
                    }
                    imgPlayButton.setOnClickListener {
                        onPlayClickListener?.invoke(movie)
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