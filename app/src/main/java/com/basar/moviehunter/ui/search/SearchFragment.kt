package com.basar.moviehunter.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
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
//
//        with(binding) {
//            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//                override fun onQueryTextSubmit(query: String?): Boolean {
//                    // on below line we are checking
//                    // if query exist or not.
////                    if (programmingLanguagesList.contains(query)) {
////                        // if query exist within list we
////                        // are filtering our list adapter.
//////                        listAdapter.filter.filter(query)
////                    } else {
////                        // if query is not present we are displaying
////                        // a toast message as no  data found..
//                    Toast.makeText(context, "onQueryTextSubmit..", Toast.LENGTH_LONG).show()
////                    }
//                    return false
//                }
//
//                override fun onQueryTextChange(newText: String?): Boolean {
//                    // if query text is change in that case we
//                    // are filtering our adapter with
//                    // new text on below line.
////                    listAdapter.filter.filter(newText)
//                    Toast.makeText(context, "onQueryTextChange..", Toast.LENGTH_LONG).show()
//
//                    return false
//                }
//            })
//        }
    }

    override fun setReceiver() {
        observe(viewModel.popularMovieListUI) { popularMovieListUI ->
            popularMovieListUI?.let {
                with(binding) {
                    tvTitle.text = "Most Searched List"
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

//        observe(viewModel.isShimmerVisible) {
//            binding.constraintLayout.visibility = if (it == false) View.VISIBLE else View.GONE
//            binding.shimmer.visibility = if (it == true) View.VISIBLE else View.GONE
//        }
    }
}