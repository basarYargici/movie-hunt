package com.basar.moviehunter.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import com.basar.moviehunter.base.BaseFragment
import com.basar.moviehunter.databinding.FragmentSearchBinding
import com.basar.moviehunter.extension.observe
import com.basar.moviehunter.extension.visibleIf
import com.basar.moviehunter.util.Receiver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(), Receiver {
    private val viewModel: SearchFragmentViewModel by viewModels()
    val adapter = SearchFragmentAdapter()

    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentSearchBinding = FragmentSearchBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        // TODO: Is it possible that not recreating fr when bottomNav changed?
        if (viewModel.isInitialized.value != true) {
            viewModel.initVM()
        }
        setReceiver()

        with(binding) {
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != null) {
                        binding.rvItems.scrollToPosition(0)
                        viewModel.getMultiSearch(query)
                        searchView.clearFocus()
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
            setAdapter()
            rvItems.adapter = adapter
        }
    }

    private fun setAdapter() {
        adapter.itemClickListener = {
            navigate(SearchFragmentDirections.actionSearchFragmentToMovieDetail(it?.id ?: 0))
        }
        adapter.onPlayClickListener = {
            viewModel.getMovieVideoPath(it?.id ?: 0)
        }
    }

    override fun setReceiver() {
        observe(viewModel.popularMovieListUI) { popularMovieListUI ->
            popularMovieListUI?.let {
                with(binding) {
                    tvTitle.text = it.title
                    adapter.submitList(it.movieList)
                }
            }
        }
        observe(viewModel.searchListUI) { popularMovieListUI ->
            popularMovieListUI?.let {
                with(binding) {
                    tvTitle.text = it.title
                    adapter.submitList(it.movieList)
                }
            }
        }
        observe(viewModel.youtubePath) { path ->
            path?.let {
                navigate(
                    SearchFragmentDirections.actionSearchFragmentToPlayerActivity(path)
                )
            }
        }

        observe(viewModel.isShimmerVisible) {
            binding.cl.visibleIf(it == false)
            binding.shimmer.visibleIf(it == true)
        }

        observe(viewModel.isNotFoundAnimVisible) {
            with(binding) {
                lottieAnimation.visibleIf(it == true)
                if (lottieAnimation.visibility == View.VISIBLE) {
                    tvTitle.text = "No results found with: ${searchView.query}"
                }
            }
        }
    }
}