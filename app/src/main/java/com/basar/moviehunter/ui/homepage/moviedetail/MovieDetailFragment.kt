package com.basar.moviehunter.ui.homepage.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.basar.moviehunter.base.BaseFragment
import com.basar.moviehunter.databinding.FragmentMovieDetailBinding
import com.basar.moviehunter.extension.getImageEndpoint
import com.basar.moviehunter.extension.observe
import com.basar.moviehunter.extension.setImageBitmap
import com.basar.moviehunter.util.Receiver
import com.basar.moviehunter.util.categoryMapper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding>(), Receiver {
    private val viewModel: MovieDetailViewModel by viewModels()
    val args: MovieDetailFragmentArgs by navArgs()

    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentMovieDetailBinding = FragmentMovieDetailBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        val movieId: Int = args.movieId
        viewModel.getDetail(movieId)
        setReceiver()
    }

    override fun setReceiver() {
        observe(viewModel.movieDetail) { movieDetail ->
            val genreList = mutableListOf<Int>()
            movieDetail?.genres?.forEach {
                it?.id?.let { genreId ->
                    genreList.add(genreId)
                }
            }
            binding.tvDetail.text =
                "Average Vote: ${movieDetail?.voteAverage} \nDate: ${movieDetail?.releaseDate}"
            binding.tvCategories.text = categoryMapper(genreList).joinToString(separator = "-")
            binding.tvDescription.text = movieDetail?.overview

            binding.imageView.setImageBitmap(getImageEndpoint(movieDetail?.posterPath))
        }
        observe(viewModel.showLoading) {
            binding.progressBar.root.visibility = if (it == true) View.VISIBLE else View.GONE
        }
    }

}