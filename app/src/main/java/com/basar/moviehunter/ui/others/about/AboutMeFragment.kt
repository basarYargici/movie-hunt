package com.basar.moviehunter.ui.others.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.basar.moviehunter.base.BaseFragment
import com.basar.moviehunter.databinding.FragmentAboutMeBinding
import com.basar.moviehunter.domain.uimodel.ConnectionButtonModel
import com.basar.moviehunter.domain.uimodel.TechnologyModel
import com.basar.moviehunter.extension.observe
import com.basar.moviehunter.util.Listener
import com.basar.moviehunter.util.Receiver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AboutMeFragment : BaseFragment<FragmentAboutMeBinding>(), Receiver, Listener {
    private val viewModel: AboutMeFragmentViewModel by viewModels()

    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentAboutMeBinding = FragmentAboutMeBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        viewModel.initVM()
        setReceiver()
        setListeners()
    }

    override fun setListeners() {
        with(binding) {
            btnLinkedin.setOnClickListener {
                viewModel.linkedActionUrl?.let { url -> openCustomTabWebpage(url) }
            }
            btnGithub.setOnClickListener {
                viewModel.githubActionUrl?.let { url -> openCustomTabWebpage(url) }
            }
            btnWebsite.setOnClickListener {
                viewModel.websiteActionUrl?.let { url -> openCustomTabWebpage(url) }
            }
            btnResume.setOnClickListener {
                viewModel.resumeActionUrl?.let { url -> openCustomTabWebpage(url) }
            }
        }
    }

    override fun setReceiver() {
        observe(viewModel.aboutUI) {
            with(binding) {
                it?.toolbarRes?.let { img -> ivMe.setImageResource(img) }
                toolbar.title = it?.toolbarTitle
                tvWelcome.text = it?.welcomeTitle
                tvDescription.text = it?.description
                tvTechnologies.text = it?.technologyListTitle
                bindToTechs(it?.technologyList)
                tvConnect.text = it?.connectTitle
                it?.connectActionList
                bindToConnections(it?.connectActionList)
            }
        }
    }

    private fun bindToTechs(technologyList: ArrayList<TechnologyModel>?) {
        with(binding.include) {
            technologyList?.mapIndexed { index, technologyModel ->
                when (index) {
                    0 -> {
                        technologyModel.imageRes?.let { ivAndroid.setImageResource(it) }
                        tvAndroid.text = technologyModel.title
                    }
                    1 -> {
                        technologyModel.imageRes?.let { ivKotlin.setImageResource(it) }
                        tvKotlin.text = technologyModel.title
                    }
                    2 -> {
                        technologyModel.imageRes?.let { ivJava.setImageResource(it) }
                        tvJava.text = technologyModel.title
                    }
                    else -> {
                        technologyModel.imageRes?.let { ivSpring.setImageResource(it) }
                        tvSpring.text = technologyModel.title
                    }
                }
            }
        }
    }

    private fun bindToConnections(connectActionList: ArrayList<ConnectionButtonModel>?) {
        with(binding) {
            connectActionList?.mapIndexed { index, connectionAction ->
                when (index) {
                    0 -> {
                        btnLinkedin.text = connectionAction.title
                        viewModel.linkedActionUrl = connectionAction.actionUrl
                    }
                    1 -> {
                        btnGithub.text = connectionAction.title
                        viewModel.githubActionUrl = connectionAction.actionUrl
                    }
                    2 -> {
                        btnWebsite.text = connectionAction.title
                        viewModel.websiteActionUrl = connectionAction.actionUrl
                    }
                    else -> {
                        btnResume.text = connectionAction.title
                        viewModel.resumeActionUrl = connectionAction.actionUrl
                    }
                }
            }
        }
    }
}