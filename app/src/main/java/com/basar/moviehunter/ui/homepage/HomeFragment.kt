package com.basar.moviehunter.ui.homepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.basar.moviehunter.base.BaseFragment
import com.basar.moviehunter.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val viewmodel: HomeFragmentViewModel by viewModels()

    override fun initViews() {
        viewmodel.initVM()
    }

    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater, container, false)
}