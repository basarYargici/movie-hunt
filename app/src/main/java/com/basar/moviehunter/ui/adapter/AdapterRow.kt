package com.basar.moviehunter.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.basar.moviehunter.R
import com.basar.moviehunter.databinding.ItemRowBinding
import com.basar.moviehunter.databinding.ItemRowHeaderBigBinding
import com.basar.moviehunter.databinding.ItemRowHeaderSmallBinding
import com.basar.moviehunter.domain.uimodel.HeaderTextStyle
import com.basar.moviehunter.domain.uimodel.RowUI
import com.basar.moviehunter.domain.uimodel.RowUI.HeaderRowUI
import com.basar.moviehunter.domain.uimodel.RowUI.TextRowUI

abstract class AdapterRow(
    var rowList: ArrayList<RowUI>? = null,
    private val hideLastDivider: Boolean = true,
//    val diffCallback: DiffUtil.ItemCallback<RowUI> = EmptyDiffCallBack()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    open fun onItemClicked(item: TextRowUI) {}

    private fun getItemLayoutId(viewType: Int) = when (viewType) {
        0 -> R.layout.item_row_header_big
        1 -> R.layout.item_row_header_small
        2 -> R.layout.item_row
        else -> -1
    }

    override fun getItemViewType(position: Int): Int = when (val item = rowList?.get(position)) {
        is HeaderRowUI -> {
            when (item.style) {
                HeaderTextStyle.BIG -> 0
                HeaderTextStyle.SMALL -> 1
            }
        }
        is TextRowUI -> 2
        else -> -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> HeaderBigRowViewHolder(
                ItemRowHeaderBigBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            1 -> HeaderSmallRowViewHolder(
                ItemRowHeaderSmallBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            2 -> TextRowViewHolder(
                ItemRowBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> TextRowViewHolder(
                ItemRowBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = rowList?.get(position)
        // TODO: how to do this better 
        when (getItemViewType(position)) {
            0 -> (holder as HeaderBigRowViewHolder).bind(item as HeaderRowUI)
            1 -> (holder as HeaderSmallRowViewHolder).bind(item as HeaderRowUI)
            2 -> (holder as TextRowViewHolder).bind(item as TextRowUI)
        }
    }

    override fun getItemCount(): Int = rowList?.size ?: 0

    inner class TextRowViewHolder(val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TextRowUI) {
            with(binding) {
                tvRow.apply {
                    text = item.text
                    item.iconRes?.let {
                        setCompoundDrawables(
                            AppCompatResources.getDrawable(this.context, it),
                            null,
                            null,
                            null
                        )
                    }
                }
                cl.setOnClickListener { onItemClicked(item) }
            }
        }
    }

    inner class HeaderBigRowViewHolder(val binding: ItemRowHeaderBigBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HeaderRowUI) {
            binding.tvHeader.text = item.text
        }
    }

    inner class HeaderSmallRowViewHolder(val binding: ItemRowHeaderSmallBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HeaderRowUI) {
            binding.tvHeader.text = item.text
        }
    }
}

class EmptyDiffCallBack<T : Any> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T) = true

    override fun areContentsTheSame(oldItem: T, newItem: T) = true
}