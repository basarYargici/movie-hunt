package com.basar.moviehunter.ui.homepage.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.basar.moviehunter.base.BaseFragment
import com.basar.moviehunter.databinding.FragmentMovieDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding>() {

    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentMovieDetailBinding = FragmentMovieDetailBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        binding.tvId.text = arguments?.get("movie_id").toString()
//        viewmodel.initVM()
//        setReceiver()
    }
}