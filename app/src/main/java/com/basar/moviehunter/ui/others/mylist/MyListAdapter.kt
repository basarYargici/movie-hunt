package com.basar.moviehunter.ui.others.mylist

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.basar.moviehunter.databinding.ItemMovieListBinding

class MyListAdapter(private var imageList: List<Bitmap>?) :
    RecyclerView.Adapter<MyListAdapter.MyListViewHolder>() {

    var itemClickListener: ((Bitmap?) -> Unit)? = null

    inner class MyListViewHolder(private val binding: ItemMovieListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val imageBitmap = imageList?.get(position)
            binding.apply {
                imageView.setImageBitmap(imageBitmap)
                materialCV.setOnClickListener {
                    // TODO: image id
//                    itemClickListener?.invoke(imageBitmap)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyListViewHolder {
        return MyListViewHolder(
            ItemMovieListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyListViewHolder, position: Int) = holder.bind(position)

    override fun getItemCount() = imageList?.size ?: 0
}