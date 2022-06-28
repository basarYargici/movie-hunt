package com.basar.moviehunter.ui.others

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.basar.moviehunter.base.BaseFragment
import com.basar.moviehunter.databinding.FragmentOthersBinding

class OthersFragment : BaseFragment<FragmentOthersBinding>() {
    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentOthersBinding = FragmentOthersBinding.inflate(layoutInflater, container, false)

    override fun initViews() {}
}