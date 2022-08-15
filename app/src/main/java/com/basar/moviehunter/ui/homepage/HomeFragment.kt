package com.basar.moviehunter.ui.homepage

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.basar.moviehunter.base.BaseFragment
import com.basar.moviehunter.databinding.FragmentHomeBinding
import com.basar.moviehunter.extension.observe
import com.basar.moviehunter.extension.visibleIf
import com.basar.moviehunter.util.Listener
import com.basar.moviehunter.util.Receiver
import com.basar.moviehunter.util.getImageUri
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(), Receiver, Listener {
    private val viewModel: HomeFragmentViewModel by viewModels()

    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        viewModel.initVM()
        setReceiver()
        setListeners()
    }

    override fun setReceiver() {
        observe(viewModel.discoverUIModel) { discoverUIModel ->
            discoverUIModel?.let {
                binding.discover.setItem(discoverUIModel, onInfoClickListener = {
                    navigateToMovieDetail(discoverUIModel.id)
                }, onPlayClickListener = {
                    navigate(
                        HomeFragmentDirections
                            .actionHomeFragmentToPlayerActivity(discoverUIModel.youtubePath ?: "")
                    )
                }, onAddToListClickListener = {
                    // TODO: Dummy solution. Must be improved
                    getImageUri(requireContext(),
                        (binding.discover.getTempImageView().drawable as BitmapDrawable).bitmap)?.let {
                        viewModel.uploadImage(discoverUIModel.id ?: 0, it)
                    }
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

    override fun setListeners() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.initVM()
            binding.swipeRefresh.isRefreshing = false
        }
    }
}