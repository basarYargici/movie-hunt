package com.basar.moviehunter.ui.homepage.moviedetail

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore.Images.Media.insertImage
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.YtFile
import com.basar.moviehunter.base.BaseFragment
import com.basar.moviehunter.databinding.FragmentMovieDetailBinding
import com.basar.moviehunter.extension.*
import com.basar.moviehunter.ui.view.movielist.MovieListAdapter
import com.basar.moviehunter.util.ConstantsHelper.MP4_BEST_QUALITY_FORMAT
import com.basar.moviehunter.util.Listener
import com.basar.moviehunter.util.Receiver
import com.basar.moviehunter.util.categoryMapper
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream


@AndroidEntryPoint
class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding>(), Receiver, Listener {
    private val viewModel: MovieDetailViewModel by viewModels()
    private val args: MovieDetailFragmentArgs by navArgs()
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
            binding.constraintLayout.visibleIf(it == false)
            binding.shimmer.visibleIf(it == true)
        }
    }

    // TODO refactor
    private fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }

    override fun setListeners() {
        binding.btnShare.setOnClickListener {
            shareMessage(viewModel.movieDetail.value.toString())
        }
        binding.btnDownload.setOnClickListener {
            requestPermissionList(permissionsRequest, PERMISSIONS)
        }
        binding.btnAddToList.setOnClickListener {
            getImageUri(requireContext(), (binding.imageView.drawable as BitmapDrawable).bitmap)?.let {
                viewModel.uploadImage(
                    viewModel.movieDetail.value?.id ?: 0,
                    it
                )
            }
        }
    }

    private fun getPermissionsRequest() =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            if (isAllPermissionsGranted(PERMISSIONS)) {
                downloadYoutubeVideo(viewModel.youtubePath.value.toString())
            } else {
                Toast.makeText(
                    context,
                    "Permission should be accepted to download the image",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    // TODO: handle extraction error, move to domain
    // TODO: can download logic moved to VM?
    /**
     * Formats:
     * 171 webm audio only DASH audio 115k , audio@128k (44100Hz), 2.59MiB (worst)
     * 140 m4a audio only DASH audio 129k , audio@128k (44100Hz), 3.02MiB
     * 141 m4a audio only DASH audio 255k , audio@256k (44100Hz), 5.99MiB
     * 160 mp4 256x144 DASH video 111k , 12fps, video only, 2.56MiB
     * 247 webm 1280x720 DASH video 1807k , 1fps, video only, 23.48MiB
     * 136 mp4 1280x720 DASH video 2236k , 24fps, video only, 27.73MiB
     * 248 webm 1920x1080 DASH video 3993k , 1fps, video only, 42.04MiB
     * 137 mp4 1920x1080 DASH video 4141k , 24fps, video only, 60.28MiB
     * 43 webm 640x360
     * 18 mp4 640x360
     * 22 mp4 1280x720 (best)
     */
    @SuppressLint("StaticFieldLeak")
    private fun downloadYoutubeVideo(youtubeLink: String) {
        return object : YouTubeExtractor(requireContext()) {
            override fun onExtractionComplete(ytFiles: SparseArray<YtFile>?, vMeta: VideoMeta?) {
                ytFiles?.let {
                    // TODO: url can be null 
                    download(ytFiles[MP4_BEST_QUALITY_FORMAT].url, vMeta?.title.toString())
                }
            }
        }.extract(youtubeLink)
    }

    private fun download(url: String, fileName: String) {
        try {
            val imageLink = Uri.parse(url)
            val req = DownloadManager.Request(imageLink)
            req.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
                .setMimeType("video/mp4")
                .setTitle(fileName)
                .setDescription("Downloading Your File")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DCIM,
                    "/moviehunter/$fileName.mp4"
                )
            val downloadManager = activity?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            downloadManager.enqueue(req)
            Toast.makeText(context, "downloading started", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show()
        }
    }
}