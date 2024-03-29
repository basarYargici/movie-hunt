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

class GetOthersUseCase @Inject constructor(
    val resProvider: ResProvider
) : UseCase<Unit, ArrayList<RowUI>>() {
    private var rowList: ArrayList<RowUI> = arrayListOf()

    override fun execute(params: Unit): Flow<ArrayList<RowUI>> = flow {
        val iconRes = R.drawable.ic_add
        val darkModeEnabled = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
        // TODO: does not get other language resources. ResProvider context might be the reason.
        rowList = arrayListOf(
            RowUI.HeaderRowUI(resProvider.getString(R.string.others_screen), HeaderTextStyle.BIG),
            RowUI.TextRowUI(iconRes, resProvider.getString(R.string.my_list)),
            RowUI.SwitchRowUI(iconRes, resProvider.getString(R.string.dark_mode), darkModeEnabled),
            RowUI.TextRowUI(iconRes, resProvider.getString(R.string.delete_downloads)),
            RowUI.TextRowUI(iconRes, resProvider.getString(R.string.about))
        )
        emit(rowList)
    }
}