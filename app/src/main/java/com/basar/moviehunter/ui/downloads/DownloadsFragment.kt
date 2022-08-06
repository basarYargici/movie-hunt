package com.basar.moviehunter.ui.downloads

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.basar.moviehunter.base.BaseFragment
import com.basar.moviehunter.data.model.DownloadedMovie
import com.basar.moviehunter.databinding.FragmentDownloadsBinding
import com.basar.moviehunter.extension.*
import com.basar.moviehunter.util.Receiver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DownloadsFragment : BaseFragment<FragmentDownloadsBinding>(), Receiver {
    private val viewModel: DownloadsViewModel by viewModels()
    private lateinit var adapter: DownloadFragmentAdapter
    private lateinit var permissionsRequest: ActivityResultLauncher<Array<String>>

    companion object {
        private val PERMISSIONS = arrayOf(READ_EXTERNAL_STORAGE)
    }

    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): FragmentDownloadsBinding = FragmentDownloadsBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        initRV()
        setReceiver()
        if (!viewModel.readPermissionGranted) {
            permissionsRequest = getPermissionsRequest()
            requestPermissionList(permissionsRequest, PERMISSIONS)
        } else {
            viewModel.initVM()
        }
    }

    private fun initRV() {
        adapter = DownloadFragmentAdapter()
        adapter.itemClickListener = {
            it?.path?.let { path ->
                navigate(
                    DownloadsFragmentDirections.actionDownloadsFragmentToLocalPlayerActivity(
                        path
                    )
                )
            }
        }
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.rvItems)
        binding.rvItems.adapter = adapter
    }

    private val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = false

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val a = mutableListOf<DownloadedMovie>()
            a.addAll(adapter.currentList)

            this@DownloadsFragment.viewModel.deleteDownloads(a[viewHolder.adapterPosition].path)

            a.removeAt(viewHolder.adapterPosition)
            adapter.submitList(a)
            if (a.isEmpty()) binding.clNoContent.setVisible()
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
                adapter.submitList(it)
            }
        }
        observe(viewModel.isShimmerVisible) {
            binding.shimmer.visibleIf(it == true)
        }
        observe(viewModel.hasDownloadedMovie) {
            binding.rvItems.visibleIf(it == true)
            binding.clNoContent.visibleIf(it == false)
        }
        observe(viewModel.isMovieDeleted) {
            if (it == true) {
                Toast.makeText(
                    context,
                    "Downloaded video deleted",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    context,
                    "There are no downloaded videos to delete",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}