package com.basar.moviehunter.ui.search

import com.basar.moviehunter.base.BaseViewModel
import com.basar.moviehunter.domain.search.SearchUseCase
import com.basar.moviehunter.extension.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchFragmentViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase
) : BaseViewModel() {

    fun initVM() {
        getMultiSearch()
    }

    private fun getMultiSearch(query: String = "al") = launch {
        searchUseCase(SearchUseCase.Params(query)).onStart {
            Timber.v("req started")
        }.onCompletion {
            Timber.v("req completed")
        }.collect {
            Timber.v(
                "getMultiSearch : "
                        + it.results?.forEach { searchResponse -> searchResponse?.name }.toString()
            )
        }
    }
}