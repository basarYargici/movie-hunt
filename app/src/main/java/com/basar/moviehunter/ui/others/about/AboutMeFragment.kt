package com.basar.moviehunter.ui.others.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.basar.moviehunter.base.BaseFragment
import com.basar.moviehunter.databinding.FragmentAboutMeBinding
import com.basar.moviehunter.extension.openDoc
import com.basar.moviehunter.util.ConstantsHelper.GITHUB_URL
import com.basar.moviehunter.util.ConstantsHelper.LINKEDIN_URL
import com.basar.moviehunter.util.ConstantsHelper.RESUME_URL
import com.basar.moviehunter.util.ConstantsHelper.WEBSITE_URL
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

    override fun initViews() {
        setListeners()
    }

    override fun setListeners() {
        with(binding) {
            btnLinkedin.setOnClickListener {
                openCustomTabWebpage(LINKEDIN_URL)
            }
            btnGithub.setOnClickListener {
                openCustomTabWebpage(GITHUB_URL)
            }
            btnWebsite.setOnClickListener {
                openCustomTabWebpage(WEBSITE_URL)
            }
            btnResume.setOnClickListener {
                openCustomTabWebpage(openDoc(RESUME_URL))
            }
        }
    }

    override fun setReceiver() {
        TODO("Not yet implemented")
    }

}