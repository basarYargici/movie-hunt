package com.basar.moviehunter.ui.others.mylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.basar.moviehunter.base.BaseFragment
import com.basar.moviehunter.databinding.FragmentMyListBinding
import com.basar.moviehunter.extension.observe
import com.basar.moviehunter.extension.visibleIf
import com.basar.moviehunter.util.Listener
import com.basar.moviehunter.util.Receiver
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MyListFragment : BaseFragment<FragmentMyListBinding>(), Receiver, Listener {
    private val viewModel: MyListFragmentViewModel by viewModels()
    private lateinit var adapter: MyListAdapter

    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentMyListBinding = FragmentMyListBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        viewModel.initVM()
        initRV()
        setReceiver()
        setListeners()
    }

    private fun initRV() {
        adapter = MyListAdapter()
        with(adapter) {
            itemClickListener = {
                Timber.d("clicked" + it?.id)
                navigate(
                    MyListFragmentDirections.actionMyListFragmentToMovieDetail(
                        it?.id ?: 0
                    )
                )
            }
            binding.rvItems.apply {
                adapter = this@MyListFragment.adapter
                setHasFixedSize(true)
            }
        }
    }

    override fun setReceiver() {
        observe(viewModel.savedMovieURL) {
            adapter.submitList(it)
        }
        observe(viewModel.isShimmerVisible) {
            binding.rvItems.visibleIf(it == false)
            binding.shimmer.visibleIf(it == true)
        }
    }

    override fun setListeners() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.initVM()
            binding.swipeRefresh.isRefreshing = false
        }
    }
}