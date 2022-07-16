package com.basar.moviehunter.ui.others.mylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.basar.moviehunter.base.BaseFragment
import com.basar.moviehunter.databinding.FragmentMyListBinding
import com.basar.moviehunter.extension.observe
import com.basar.moviehunter.util.Listener
import com.basar.moviehunter.util.Receiver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyListFragment : BaseFragment<FragmentMyListBinding>(), Receiver, Listener {
    private val viewModel: MyListFragmentViewModel by viewModels()

    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentMyListBinding = FragmentMyListBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        setReceiver()
        viewModel.downloadImage()
        with(binding) {
            rvItems.apply {
                with(MyListAdapter(viewModel.savedMovieURL.value)) {
//                    itemClickListener = onClickListener
                    adapter = this
                }
//            addItemDecoration()
                setHasFixedSize(true)
            }
        }
    }

    override fun setListeners() {
        TODO("Not yet implemented")
    }

    override fun setReceiver() {
        observe(viewModel.savedMovieURL) {
            // TODO: wrong
            binding.rvItems.adapter = MyListAdapter(it)
        }
    }
}