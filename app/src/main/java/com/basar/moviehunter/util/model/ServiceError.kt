package com.basar.moviehunter.util.model

sealed class ServiceError {
    object NetworkError : ServiceError()
    class BusinessError(val message: String, val code: Int = 0) : ServiceError()
    class GenericError(val message: String?) : ServiceError()
    object TimeOutError : ServiceError()
    object ProxyError : ServiceError()
}