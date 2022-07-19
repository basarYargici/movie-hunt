package com.basar.moviehunter.ui.others.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.basar.moviehunter.base.BaseFragment
import com.basar.moviehunter.databinding.FragmentAboutMeBinding
import com.basar.moviehunter.util.Listener
import com.basar.moviehunter.util.Receiver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AboutMeFragment : BaseFragment<FragmentAboutMeBinding>(), Receiver, Listener {
    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentAboutMeBinding = FragmentAboutMeBinding.inflate(layoutInflater, container, false)

    override fun initViews() {}

    override fun setListeners() {
        TODO("Not yet implemented")
    }

    override fun setReceiver() {
        TODO("Not yet implemented")
    }

}