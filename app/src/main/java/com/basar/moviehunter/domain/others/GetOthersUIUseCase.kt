package com.basar.moviehunter.domain.others

import androidx.appcompat.app.AppCompatDelegate
import com.basar.moviehunter.R
import com.basar.moviehunter.domain.uimodel.HeaderTextStyle
import com.basar.moviehunter.domain.uimodel.RowUI
import com.basar.moviehunter.util.ResProvider
import com.example.core.base.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetOthersUIUseCase @Inject constructor(
    val resProvider: ResProvider
) : UseCase<Unit, ArrayList<RowUI>>() {
    private var rowList: ArrayList<RowUI> = arrayListOf()

    override fun execute(params: Unit): Flow<ArrayList<RowUI>> = flow {
        val iconRes = R.drawable.ic_add
        val darkModeEnabled = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
        rowList = arrayListOf(
            RowUI.HeaderRowUI(resProvider.getString(R.string.others_screen), HeaderTextStyle.BIG),
            RowUI.TextRowUI("1", iconRes, resProvider.getString(R.string.my_list)),
            RowUI.TextRowUI("2", iconRes, resProvider.getString(R.string.settings)),
            RowUI.SwitchRowUI("3", iconRes, resProvider.getString(R.string.dark_mode), darkModeEnabled),
            RowUI.TextRowUI("4", iconRes, resProvider.getString(R.string.delete_downloads)),
            RowUI.TextRowUI("5", iconRes, resProvider.getString(R.string.about))
        )
        emit(rowList)
    }
}