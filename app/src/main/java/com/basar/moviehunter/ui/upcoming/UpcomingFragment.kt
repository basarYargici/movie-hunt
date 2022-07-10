package com.basar.moviehunter.ui.upcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
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

    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentUpcomingBinding = FragmentUpcomingBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        viewModel.initVM()
        setReceiver()
    }

    override fun setReceiver() {
        observe(viewModel.upcomingMoviesUI) {
            Timber.d("++upcoming: $it")
        }
    }
}