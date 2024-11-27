package com.est.munchy.utils

sealed class NetworkResponse<T> (
    val data: T? = null,
    val message: String? = null
){
    class  SuccessResponse<T>(data: T) : NetworkResponse<T>(data)
    class  ErrorResponse<T>(message: String?, data: T? = null) : NetworkResponse<T>(data, message)
    class  Loading<T> : NetworkResponse<T>()
}