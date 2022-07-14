package com.basar.moviehunter.ui.others

import androidx.lifecycle.MutableLiveData
import com.basar.moviehunter.R
import com.basar.moviehunter.base.BaseViewModel
import com.basar.moviehunter.domain.uimodel.HeaderTextStyle
import com.basar.moviehunter.domain.uimodel.RowUI
import com.basar.moviehunter.extension.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class OthersFragmentViewModel @Inject constructor(
) : BaseViewModel() {
    val rowUIList = MutableLiveData<ArrayList<RowUI>>()
    private var rowList: ArrayList<RowUI> = arrayListOf()

    fun initVM() {
        getRowList()
    }

    private fun getRowList() = launch {
        flow {
            val iconRes = R.drawable.ic_add
            rowList = arrayListOf(
                RowUI.HeaderRowUI("Others Screen", HeaderTextStyle.BIG),
                RowUI.TextRowUI("1", iconRes, "Listem"),
                RowUI.TextRowUI("2", iconRes, "Ayarlar"),
                RowUI.TextRowUI("3", iconRes, "Tema"),
                RowUI.TextRowUI("4", iconRes, "İndirilenleri Sil"),
                RowUI.TextRowUI("5", iconRes, "App Version")
            )
            emit(rowList)
        }.onStart {
            showLoading()
        }.onCompletion {
            hideLoading()
        }.collect {
            rowUIList.postValue(it)
        }
    }
}