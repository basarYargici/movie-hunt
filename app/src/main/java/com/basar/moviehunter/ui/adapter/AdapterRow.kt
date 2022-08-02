package com.basar.moviehunter.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.basar.moviehunter.databinding.ItemRowBinding
import com.basar.moviehunter.databinding.ItemRowHeaderBigBinding
import com.basar.moviehunter.databinding.ItemRowHeaderSmallBinding
import com.basar.moviehunter.databinding.ItemSwitchRowBinding
import com.basar.moviehunter.domain.uimodel.HeaderTextStyle
import com.basar.moviehunter.domain.uimodel.RowUI
import com.basar.moviehunter.domain.uimodel.RowUI.*

abstract class AdapterRow(
    var rowList: ArrayList<RowUI>? = null,
    //    val diffCallback: DiffUtil.ItemCallback<RowUI> = EmptyDiffCallBack()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    open fun onItemClicked(item: TextRowUI) {}
    open fun onItemCheckChanged(item: SwitchRowUI) {}

    override fun getItemViewType(position: Int): Int = when (val item = rowList?.get(position)) {
        is HeaderRowUI -> {
            when (item.style) {
                HeaderTextStyle.BIG -> 0
                HeaderTextStyle.SMALL -> 1
            }
        }
        is TextRowUI -> 2
        is SwitchRowUI -> 3
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
            3 -> SwitchRowViewHolder(
                ItemSwitchRowBinding.inflate(
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
            3 -> (holder as SwitchRowViewHolder).bind(item as SwitchRowUI)
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

    inner class SwitchRowViewHolder(val binding: ItemSwitchRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SwitchRowUI) {
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
                smSwitch.apply {
                    isChecked = (item.isChecked == true)
                    setOnClickListener { onItemCheckChanged(item) }
                }
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
