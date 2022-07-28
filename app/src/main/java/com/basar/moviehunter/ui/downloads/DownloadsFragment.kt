package com.basar.moviehunter.ui.downloads

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.basar.moviehunter.base.BaseFragment
import com.basar.moviehunter.databinding.FragmentDownloadsBinding
import com.basar.moviehunter.extension.*
import com.basar.moviehunter.util.Receiver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DownloadsFragment : BaseFragment<FragmentDownloadsBinding>(), Receiver {
    private val viewModel: DownloadsViewModel by viewModels()
    private val adapter = DownloadFragmentAdapter()
    private lateinit var permissionsRequest: ActivityResultLauncher<Array<String>>

    companion object {
        private val PERMISSIONS = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): FragmentDownloadsBinding = FragmentDownloadsBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        if (!viewModel.readPermissionGranted) {
            permissionsRequest = getPermissionsRequest()
            requestPermissionList(permissionsRequest, PERMISSIONS)
        } else {
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

    private fun getPermissionsRequest() =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            if (isAllPermissionsGranted(PERMISSIONS)) {
                viewModel.readPermissionGranted = true
                viewModel.initVM()
            } else {
                binding.clNoContent.setVisible()

                Toast.makeText(
                    context,
                    "Permission should be accepted to download the image",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    override fun setReceiver() {
        observe(viewModel.downloadedMoviesUI) { popularMovieListUI ->
            popularMovieListUI?.let {
                adapter.movieList = it
                adapter.notifyDataSetChanged()
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