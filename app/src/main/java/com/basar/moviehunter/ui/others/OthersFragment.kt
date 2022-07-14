package com.basar.moviehunter.ui.others

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.basar.moviehunter.base.BaseFragment
import com.basar.moviehunter.databinding.FragmentOthersBinding
import com.basar.moviehunter.domain.uimodel.RowUI
import com.basar.moviehunter.extension.observe
import com.basar.moviehunter.ui.adapter.AdapterRow
import com.basar.moviehunter.util.Receiver

class OthersFragment : BaseFragment<FragmentOthersBinding>(), Receiver {
    lateinit var rowAdapter: AdapterRow
    private val viewModel: OthersFragmentViewModel by viewModels()

    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentOthersBinding = FragmentOthersBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        viewModel.initVM()
        setReceiver()
        rowAdapter = object : AdapterRow() {
            override fun onItemClicked(item: RowUI.TextRowUI) {
                when (item.text) {
                    // TODO: string res
                    "Listem" -> {
                        // TODO: navigate
                        Toast.makeText(context, "a clicked", Toast.LENGTH_SHORT).show()
                    }
                    "Ayarlar" -> {
                        // TODO: navigate
                        Toast.makeText(context, "a clicked", Toast.LENGTH_SHORT).show()
                    }
                    "Tema" -> {
                        // TODO: navigate
                        Toast.makeText(context, "a clicked", Toast.LENGTH_SHORT).show()
                    }
                    "İndirilenleri Sil" -> {
                        // TODO: navigate
                        Toast.makeText(context, "a clicked", Toast.LENGTH_SHORT).show()
                    }
                    "App Version" -> {
                        // TODO: navigate
                        Toast.makeText(context, "a clicked", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }


        binding.rvItems.apply {
            adapter = rowAdapter
            setHasFixedSize(true)
        }
    }

    override fun setReceiver() {
        observe(viewModel.rowUIList) {
            rowAdapter.rowList = it
            rowAdapter.notifyDataSetChanged()
        }
    }
}