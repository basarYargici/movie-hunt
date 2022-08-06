package com.basar.moviehunter.ui.others.mylist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.basar.moviehunter.databinding.ItemMovieListBinding
import com.basar.moviehunter.domain.uimodel.MyListUI
import timber.log.Timber

class MyListAdapter : ListAdapter<MyListUI, MyListAdapter.MyListViewHolder>(DiffCallback()) {
    var itemClickListener: ((MyListUI?) -> Unit)? = null

    private class DiffCallback : DiffUtil.ItemCallback<MyListUI>() {
        override fun areItemsTheSame(oldItem: MyListUI, newItem: MyListUI) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MyListUI, newItem: MyListUI) =
            oldItem == newItem
    }

    inner class MyListViewHolder(private val binding: ItemMovieListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val listItem = getItem(position)
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

    override fun getItemCount() = currentList.size
}