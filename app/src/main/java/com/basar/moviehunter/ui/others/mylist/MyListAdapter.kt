package com.basar.moviehunter.ui.others.mylist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.basar.moviehunter.databinding.ItemMovieListBinding
import com.basar.moviehunter.domain.uimodel.MyListUI
import timber.log.Timber

class MyListAdapter :
    RecyclerView.Adapter<MyListAdapter.MyListViewHolder>() {
    var imageList: List<MyListUI>? = null
    var itemClickListener: ((MyListUI?) -> Unit)? = null

    inner class MyListViewHolder(private val binding: ItemMovieListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val listItem = imageList?.get(position)
            binding.apply {
                imageView.setImageBitmap(listItem?.image)
                materialCV.setOnClickListener {
                    Timber.d("id: " + listItem?.id)
                    itemClickListener?.invoke(listItem)
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