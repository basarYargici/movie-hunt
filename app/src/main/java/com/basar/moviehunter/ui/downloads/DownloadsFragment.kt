package com.basar.moviehunter.ui.downloads

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.basar.moviehunter.base.BaseFragment
import com.basar.moviehunter.databinding.FragmentDownloadsBinding
import com.basar.moviehunter.extension.observe
import com.basar.moviehunter.extension.visibleIf
import com.basar.moviehunter.util.Receiver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DownloadsFragment : BaseFragment<FragmentDownloadsBinding>(), Receiver {
    private val viewModel: DownloadsViewModel by viewModels()
    private val adapter = DownloadFragmentAdapter()
    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentDownloadsBinding = FragmentDownloadsBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            viewModel.initVM()
        }
        setReceiver()

        with(binding) {
            adapter.itemClickListener = {
                it?.path?.let { path ->
                    navigate(
                        DownloadsFragmentDirections.actionDownloadsFragmentToLocalPlayerActivity(
                            path
                        )
                    )
                }
                Toast.makeText(context, "itemClickListener", Toast.LENGTH_SHORT).show()
            }
            rvItems.adapter = adapter
        }
    }

    override fun setReceiver() {
        observe(viewModel.downloadedMoviesUI) { popularMovieListUI ->
            popularMovieListUI?.let {
                adapter.movieList = it
            }
        }
        observe(viewModel.isShimmerVisible) {
            binding.shimmer.visibleIf(it == true)
        }
        observe(viewModel.hasDownloadedMovie) {
            binding.rvItems.visibleIf(it == true)
            binding.clNoContent.visibleIf(it == false)
        }
    }
}