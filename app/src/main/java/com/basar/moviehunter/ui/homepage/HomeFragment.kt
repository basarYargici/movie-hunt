package com.basar.moviehunter.ui.homepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.basar.moviehunter.base.BaseFragment
import com.basar.moviehunter.databinding.FragmentHomeBinding
import com.basar.moviehunter.extension.observe
import com.basar.moviehunter.util.Listener
import com.basar.moviehunter.util.Receiver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(), Receiver, Listener {
    private val viewmodel: HomeFragmentViewModel by viewModels()

    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        viewmodel.initVM()
        setListeners()
        setReceiver()
    }

    override fun setReceiver() {
        observe(viewmodel.discoverUIModel) { discoverUIModel ->
            discoverUIModel?.let {
                binding.discover.setItem(it)
            }
        }
        observe(viewmodel.showLoading) {
            binding.progressBar.root.visibility = if (it == true) VISIBLE else GONE
        }
    }

    override fun setListeners() {
        binding.btnGo.setOnClickListener {
            navigate(HomeFragmentDirections.actionHomeFragmentToMovieDetailFragment())
        }
    }
}