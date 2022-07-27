package com.basar.moviehunter.ui.homepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.basar.moviehunter.base.BaseFragment
import com.basar.moviehunter.databinding.FragmentHomeBinding
import com.basar.moviehunter.extension.observe
import com.basar.moviehunter.extension.visibleIf
import com.basar.moviehunter.util.Receiver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(), Receiver {
    private val viewModel: HomeFragmentViewModel by viewModels()

    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        viewModel.initVM()
        setReceiver()
    }

    override fun setReceiver() {
        observe(viewModel.discoverUIModel) { discoverUIModel ->
            discoverUIModel?.let {
                binding.discover.setItem(it, onInfoClickListener = {
                    navigateToMovieDetail(it.id)
                }, onPlayClickListener = {
                    navigate(HomeFragmentDirections.actionHomeFragmentToPlayerActivity(it.youtubePath ?: ""))
                }, onAddToListClickListener = {
                    Toast.makeText(context, "Add To List", Toast.LENGTH_SHORT).show()
                })
            }
        }
        observe(viewModel.popularMovieListUI) { popularMovieListUI ->
            popularMovieListUI?.let {
                binding.popularMovieList.setItem(popularMovieListUI, onClickListener = {
                    navigateToMovieDetail(it?.id)
                })
            }
        }
        observe(viewModel.topRatedMovieListUI) { topMovieListUI ->
            topMovieListUI?.let {
                binding.topMovieList.setItem(topMovieListUI, onClickListener = {
                    navigateToMovieDetail(it?.id)
                })
            }
        }
        observe(viewModel.isShimmerVisible) {
            binding.constraintLayout.visibleIf(it == false)
            binding.shimmer.visibleIf(it == true)
        }
    }

    private fun navigateToMovieDetail(id: Int?) {
        navigate(HomeFragmentDirections.actionHomeFragmentToMovieDetail(id ?: 0))
    }
}