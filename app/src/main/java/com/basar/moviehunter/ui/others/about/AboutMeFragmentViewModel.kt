package com.basar.moviehunter.ui.others.about

import androidx.lifecycle.MutableLiveData
import com.basar.moviehunter.base.BaseViewModel
import com.basar.moviehunter.domain.others.GetAboutUseCase
import com.basar.moviehunter.domain.uimodel.AboutUI
import com.basar.moviehunter.extension.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class AboutMeFragmentViewModel @Inject constructor(
    private val getAboutUseCase: GetAboutUseCase
) : BaseViewModel() {
    val aboutUI = MutableLiveData<AboutUI>()
    var linkedActionUrl: String? = null
    var githubActionUrl: String? = null
    var websiteActionUrl: String? = null
    var resumeActionUrl: String? = null

    fun initVM() {
        getAbout()
    }

    private fun getAbout() = launch {
        getAboutUseCase(Unit).onStart {
            // TODO: add shimmer or loading
            showLoading()
        }.onCompletion {
            hideLoading()
        }.collect {
            aboutUI.postValue(it)
        }
    }
}