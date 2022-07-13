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
import com.basar.moviehunter.util.Receiver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DownloadsFragment : BaseFragment<FragmentDownloadsBinding>(), Receiver {
    private val viewModel: DownloadsViewModel by viewModels()
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
    }

    override fun setReceiver() {
        observe(viewModel.downloadedMoviesUI) { popularMovieListUI ->
            popularMovieListUI?.let {
                with(binding) {
                    // TODO: leak each update?
                    val adapter = DownloadFragmentAdapter(it)
                    adapter.itemClickListener = {
                        Toast.makeText(context, "itemClickListener", Toast.LENGTH_SHORT).show()
                    }
                    adapter.onPlayClickListener = {
                        Toast.makeText(context, "onPlayClickListener", Toast.LENGTH_SHORT).show()
                    }
                    rvItems.adapter = adapter
                }
            }
        }
    }
}