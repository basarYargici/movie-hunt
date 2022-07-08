package com.basar.moviehunter.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import com.basar.moviehunter.base.BaseFragment
import com.basar.moviehunter.databinding.FragmentSearchBinding
import com.basar.moviehunter.extension.observe
import com.basar.moviehunter.util.Receiver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(), Receiver {
    private val viewModel: SearchFragmentViewModel by viewModels()

    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentSearchBinding = FragmentSearchBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        viewModel.initVM()
        setReceiver()

        with(binding) {
            // TODO: if search result is null, show lotti
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    Toast.makeText(context, "onQueryTextSubmit..", Toast.LENGTH_LONG).show()
                    if (query != null) {
                        binding.rvItems.scrollToPosition(0)
                        viewModel.getMultiSearch(query)
                        searchView.clearFocus()
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    Toast.makeText(context, "onQueryTextChange..", Toast.LENGTH_LONG).show()
                    return false
                }
            })
        }
    }

    override fun setReceiver() {
        observe(viewModel.popularMovieListUI) { popularMovieListUI ->
            popularMovieListUI?.let {
                with(binding) {
                    tvTitle.text = it.title
                    val adapter = SearchFragmentAdapter(it.movieList)
                    adapter.itemClickListener = {
                        navigate(
                            SearchFragmentDirections.actionSearchFragmentToMovieDetail(
                                it?.id ?: 0
                            )
                        )
                    }
                    adapter.onPlayClickListener = {
                        Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
                        viewModel.getMovieVideoPath(it?.id ?: 0)
                    }
                    rvItems.adapter = adapter
                }
            }
        }
        observe(viewModel.searchListUI) { popularMovieListUI ->
            popularMovieListUI?.let {
                with(binding) {
                    tvTitle.text = it.title
                    val adapter = SearchFragmentAdapter(it.movieList)
                    adapter.itemClickListener = {
                        navigate(
                            SearchFragmentDirections.actionSearchFragmentToMovieDetail(
                                it?.id ?: 0
                            )
                        )
                    }
                    adapter.onPlayClickListener = {
                        Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
                        viewModel.getMovieVideoPath(it?.id ?: 0)
                    }
                    rvItems.adapter = adapter
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

        // TODO: refactor to base class
        observe(viewModel.isShimmerVisible) {
            binding.cl.visibility = if (it == false) View.VISIBLE else View.GONE
            binding.shimmer.visibility = if (it == true) View.VISIBLE else View.GONE
        }
    }
}