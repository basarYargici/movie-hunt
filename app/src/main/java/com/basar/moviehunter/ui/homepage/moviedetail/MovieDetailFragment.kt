package com.basar.moviehunter.ui.homepage.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.basar.moviehunter.base.BaseFragment
import com.basar.moviehunter.databinding.FragmentMovieDetailBinding
import com.basar.moviehunter.extension.getImageEndpoint
import com.basar.moviehunter.extension.observe
import com.basar.moviehunter.extension.setImageBitmap
import com.basar.moviehunter.ui.view.MovieListAdapter
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
        viewModel.initVM(movieId)
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
            with(binding) {
                tvDetail.text =
                    "Average Vote: ${movieDetail?.voteAverage} Date: ${movieDetail?.releaseDate}"
                tvCategories.text = categoryMapper(genreList).joinToString(separator = "-")
                tvDescription.text = movieDetail?.overview
                imageView.setImageBitmap(getImageEndpoint(movieDetail?.posterPath))
            }
        }

        observe(viewModel.similarMovies) { similarMovies ->
            binding.rvItems.apply {
                with(MovieListAdapter(similarMovies?.results?.filterNotNull())) {
                    itemClickListener = {
                        navigate(MovieDetailFragmentDirections.actionMovieDetailFragmentSelf(it?.id ?: 0))
                        Toast.makeText(context, it?.id.toString(), Toast.LENGTH_SHORT).show()
                    }
                    adapter = this
                }
                layoutManager = GridLayoutManager(context, 2)
            }
        }
        observe(viewModel.youtubePath) { path ->
            binding.btnPlay.setOnClickListener {
                navigate(MovieDetailFragmentDirections.actionMovieDetailFragmentToPlayerActivity(path ?: ""))
            }
        }
        observe(viewModel.isShimmerVisible) {
            binding.constraintLayout.visibility = if (it == false) View.VISIBLE else View.GONE
            binding.shimmer.visibility = if (it == true) View.VISIBLE else View.GONE
        }
    }
}