package com.example.core.base

import kotlinx.coroutines.flow.Flow

abstract class UseCase<P, R> {
    operator fun invoke(params: P): Flow<R> = execute(params)

    protected abstract fun execute(params: P): Flow<R>
}
