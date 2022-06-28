package com.basar.moviehunter.ui.downloads

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.basar.moviehunter.base.BaseFragment
import com.basar.moviehunter.databinding.FragmentDownloadsBinding

class DownloadsFragment : BaseFragment<FragmentDownloadsBinding>() {
    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentDownloadsBinding = FragmentDownloadsBinding.inflate(layoutInflater, container, false)

    override fun initViews() {}
}