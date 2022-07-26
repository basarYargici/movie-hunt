package com.basar.moviehunter.extension

import androidx.lifecycle.viewModelScope
import com.basar.moviehunter.base.BaseViewModel
import kotlinx.coroutines.*

fun BaseViewModel.launch(block: suspend CoroutineScope.() -> Unit): Job {
    return viewModelScope.launch(exceptionHandler, CoroutineStart.DEFAULT, block)
}

fun BaseViewModel.launchInIO(block: suspend CoroutineScope.() -> Unit): Job {
    return viewModelScope.launch(Dispatchers.IO, CoroutineStart.DEFAULT, block)
}