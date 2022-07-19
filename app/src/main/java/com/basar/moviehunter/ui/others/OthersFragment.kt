package com.basar.moviehunter.ui.others

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.basar.moviehunter.R
import com.basar.moviehunter.base.BaseActivity
import com.basar.moviehunter.base.BaseFragment
import com.basar.moviehunter.databinding.FragmentOthersBinding
import com.basar.moviehunter.domain.uimodel.RowUI
import com.basar.moviehunter.extension.observe
import com.basar.moviehunter.ui.adapter.AdapterRow
import com.basar.moviehunter.util.Listener
import com.basar.moviehunter.util.Receiver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OthersFragment : BaseFragment<FragmentOthersBinding>(), Receiver, Listener {
    private lateinit var rowAdapter: AdapterRow
    private val viewModel: OthersFragmentViewModel by viewModels()

    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentOthersBinding = FragmentOthersBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        viewModel.setTurkish((activity as BaseActivity<*>).isContentTurkish())
        viewModel.initVM()
        setReceiver()
        setListeners()
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
                        navigate(OthersFragmentDirections.actionOthersFragmentToAboutMeFragment())
//                        openCustomTabWebpage(WEBSITE_URL)
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
        observe(viewModel.turkishLanguage) {
            with(binding) {
                if (it == true) {
                    tvTurkish.typeface = Typeface.DEFAULT_BOLD
                    tvEnglish.typeface = Typeface.DEFAULT
                } else {
                    tvTurkish.typeface = Typeface.DEFAULT
                    tvEnglish.typeface = Typeface.DEFAULT_BOLD
                }
            }
        }
    }

    override fun setListeners() {
        binding.mcvLanguage.setOnClickListener {
            (activity as BaseActivity<*>).appRepository.setReversedLanguage()
            (activity as BaseActivity<*>).recreate()
        }
    }
}