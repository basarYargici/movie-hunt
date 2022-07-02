package com.basar.moviehunter.ui.homepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
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
                binding.discover.setItem(it, onInfoClickListener = {
                    navigate(HomeFragmentDirections.actionHomeFragmentToMovieDetailFragment())
                }, onPlayClickListener = {
                    Toast.makeText(context, "ExoPlayer", Toast.LENGTH_SHORT).show()
                }, onAddToListClickListener = {
                    Toast.makeText(context, "Add To List", Toast.LENGTH_SHORT).show()
                })
            }
        }
        observe(viewmodel.popularMovieListUI) { popularMovieListUI ->
            popularMovieListUI?.let {
                binding.popularMovieList.setItem(popularMovieListUI, onClickListener = {
                    navigate(HomeFragmentDirections.actionHomeFragmentToMovieDetailFragment(it?.id ?: 0))
                })
            }
        }
        observe(viewmodel.topRatedMovieListUI) { topMovieListUI ->
            topMovieListUI?.let {
                binding.topMovieList.setItem(topMovieListUI, onClickListener = {
                    navigate(HomeFragmentDirections.actionHomeFragmentToMovieDetailFragment(it?.id ?: 0))
                })
            }
        }
        observe(viewmodel.showLoading) {
            binding.progressBar.root.visibility = if (it == true) VISIBLE else GONE
        }
    }

    override fun setListeners() {
//        binding.btnGo.setOnClickListener {
//            navigate(HomeFragmentDirections.actionHomeFragmentToMovieDetailFragment())
//        }
    }
}