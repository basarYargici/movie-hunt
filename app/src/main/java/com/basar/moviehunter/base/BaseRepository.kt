package com.basar.moviehunter.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

open class BaseRepository {
    fun <T> sendRequest(call: suspend () -> T): Flow<T> = flow {
        val response = call.invoke()
        emit(response)
    }.flowOn(Dispatchers.IO)
}