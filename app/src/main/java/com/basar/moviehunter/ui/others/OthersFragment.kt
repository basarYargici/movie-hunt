package com.basar.moviehunter.ui.others

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.basar.moviehunter.R
import com.basar.moviehunter.base.BaseFragment
import com.basar.moviehunter.databinding.FragmentOthersBinding
import com.basar.moviehunter.domain.uimodel.RowUI
import com.basar.moviehunter.extension.observe
import com.basar.moviehunter.ui.adapter.AdapterRow
import com.basar.moviehunter.util.ConstantsHelper.WEBSITE_URL
import com.basar.moviehunter.util.Receiver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OthersFragment : BaseFragment<FragmentOthersBinding>(), Receiver {
    private lateinit var rowAdapter: AdapterRow
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
                    resProvider.getString(R.string.my_list) -> {
                        showToast(resProvider.getString(R.string.my_list) + " clicked")
                        navigate(OthersFragmentDirections.actionOthersFragmentToMyListFragment())
                    }
                    resProvider.getString(R.string.settings) -> {
                        showToast(resProvider.getString(R.string.settings) + " clicked")
                    }
                    resProvider.getString(R.string.dark_mode) -> {
                        showToast(resProvider.getString(R.string.dark_mode) + " clicked")
                    }
                    resProvider.getString(R.string.delete_downloads) -> {
                        showToast(resProvider.getString(R.string.delete_downloads) + " clicked")
                    }
                    resProvider.getString(R.string.about) -> {
                        openCustomTabWebpage(WEBSITE_URL)
                    }
                }
            }

            override fun onItemCheckChanged(item: RowUI.SwitchRowUI) {
                with(item) {
                    if (text == resProvider.getString(R.string.dark_mode)) {
                        isChecked = isChecked?.not()
                        viewModel.changeDarkModeSelection(isChecked == true)
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