package com.basar.moviehunter.ui.upcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.basar.moviehunter.base.BaseFragment
import com.basar.moviehunter.databinding.FragmentUpcomingBinding
import com.basar.moviehunter.extension.observe
import com.basar.moviehunter.util.Receiver
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class UpcomingFragment : BaseFragment<FragmentUpcomingBinding>(), Receiver {
    private val viewModel: UpcomingViewModel by viewModels()
    private lateinit var adapterUpcoming: UpcomingFragmentAdapter

    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentUpcomingBinding = FragmentUpcomingBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        initRV()
        viewModel.initVM()
        setReceiver()
    }

    private fun initRV() {
        adapterUpcoming = UpcomingFragmentAdapter()
        adapterUpcoming.playClickListener = {
            Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
            // TODO: async problem and catch required if we do not have any youtubePath
            viewModel.getTutorialLink(it?.id ?: 0)
            navigate(
                UpcomingFragmentDirections.actionUpcomingFragmentToPlayerActivity(
                    viewModel.youtubePath.value ?: ""
                )
            )
        }
        adapterUpcoming.shareClickListener = {
            shareMessage(it.toString())
        }
        binding.rvItems.adapter = adapterUpcoming
    }

    override fun setReceiver() {
        observe(viewModel.upcomingMoviesUI) {
            Timber.d("++upcoming: $it")
            adapterUpcoming.submitList(it)
        }
        observe(viewModel.youtubePath) {
            Timber.d("++upcoming: $it")
        }
    }
}