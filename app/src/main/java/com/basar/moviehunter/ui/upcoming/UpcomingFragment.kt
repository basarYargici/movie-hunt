package com.basar.moviehunter.ui.upcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.basar.moviehunter.base.BaseFragment
import com.basar.moviehunter.databinding.FragmentUpcomingBinding

class UpcomingFragment : BaseFragment<FragmentUpcomingBinding>() {
    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentUpcomingBinding = FragmentUpcomingBinding.inflate(layoutInflater, container, false)

    override fun initViews() {}
}