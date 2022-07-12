package com.basar.moviehunter.ui.homepage.moviedetail

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.basar.moviehunter.base.BaseFragment
import com.basar.moviehunter.databinding.FragmentMovieDetailBinding
import com.basar.moviehunter.extension.*
import com.basar.moviehunter.ui.view.movielist.MovieListAdapter
import com.basar.moviehunter.util.Listener
import com.basar.moviehunter.util.Receiver
import com.basar.moviehunter.util.categoryMapper
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding>(), Receiver, Listener {
    private val viewModel: MovieDetailViewModel by viewModels()
    val args: MovieDetailFragmentArgs by navArgs()
    private var isPermissionGranted = false

    private lateinit var permissionsRequest: ActivityResultLauncher<Array<String>>

    companion object {
        private val PERMISSIONS = arrayOf(WRITE_EXTERNAL_STORAGE)
    }

    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentMovieDetailBinding = FragmentMovieDetailBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        val movieId: Int = args.movieId
        viewModel.initVM(movieId)
        setReceiver()
        setListeners()
        permissionsRequest = getPermissionsRequest()
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
                tvDetail.text = "Average Vote: ${movieDetail?.voteAverage} Date: ${movieDetail?.releaseDate}"
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

    override fun setListeners() {
        binding.btnShare.setOnClickListener {
            shareMessage(viewModel.movieDetail.value.toString())
        }
        binding.btnDownload.setOnClickListener {
            requestPermissionList(permissionsRequest, PERMISSIONS)
        }
    }

    private fun getPermissionsRequest() =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            if (isAllPermissionsGranted(PERMISSIONS)) {             //extension function
                downloadImage(
                    "https://img.freepik.com/premium-vector/warrior-logo-gaming_43623-412.jpg?w=2000",
                    "warrior"
                )
            } else {
                Toast.makeText(
                    context,
                    "Permission should be accepted to download the image",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    private fun downloadImage(url: String, fileName: String) {
        try {
            var downloadManager = activity?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val imageLink = Uri.parse(url)
            var req = DownloadManager.Request(imageLink)
            req.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
                .setMimeType("image/jpeg")
                .setAllowedOverRoaming(false)
                .setTitle(fileName)
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_PICTURES, File.separator + fileName + ".jpg"
                )
            downloadManager.enqueue(req)
            Toast.makeText(context, "downloaded", Toast.LENGTH_SHORT).show()

        } catch (e: Exception) {
            Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show()
        }
    }
}