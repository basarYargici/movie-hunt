package com.basar.moviehunter.ui.others

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.MutableLiveData
import com.basar.moviehunter.base.BaseViewModel
import com.basar.moviehunter.base.app.AppRepository
import com.basar.moviehunter.domain.others.GetOthersUIUseCase
import com.basar.moviehunter.domain.uimodel.RowUI
import com.basar.moviehunter.extension.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class OthersFragmentViewModel @Inject constructor(
    private var appRepository: AppRepository,
    private val getOthersUIUseCase: GetOthersUIUseCase
) : BaseViewModel() {
    val rowUIList = MutableLiveData<ArrayList<RowUI>>()

    fun initVM() {
        getRowList()
    }

    private fun getRowList() = launch {
        getOthersUIUseCase(Unit).onStart {
            showLoading()
        }.onCompletion {
            hideLoading()
        }.collect {
            rowUIList.postValue(it)
        }
    }

    fun changeDarkModeSelection(checked: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (checked) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
        appRepository.setDarkModeState(AppCompatDelegate.getDefaultNightMode())
    }

}