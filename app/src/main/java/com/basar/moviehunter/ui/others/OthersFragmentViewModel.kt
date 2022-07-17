package com.basar.moviehunter.ui.others

import androidx.lifecycle.MutableLiveData
import com.basar.moviehunter.R
import com.basar.moviehunter.base.BaseViewModel
import com.basar.moviehunter.domain.uimodel.HeaderTextStyle
import com.basar.moviehunter.domain.uimodel.RowUI
import com.basar.moviehunter.extension.launch
import com.basar.moviehunter.util.ResProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class OthersFragmentViewModel @Inject constructor(
    private var resProvider: ResProvider
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
                RowUI.HeaderRowUI(resProvider.getString(R.string.my_list), HeaderTextStyle.BIG),
                RowUI.TextRowUI("1", iconRes, resProvider.getString(R.string.my_list)),
                RowUI.TextRowUI("2", iconRes, resProvider.getString(R.string.settings)),
                RowUI.TextRowUI("3", iconRes, resProvider.getString(R.string.theme)),
                RowUI.TextRowUI("4", iconRes, resProvider.getString(R.string.delete_downloads)),
                RowUI.TextRowUI("5", iconRes, resProvider.getString(R.string.about))
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