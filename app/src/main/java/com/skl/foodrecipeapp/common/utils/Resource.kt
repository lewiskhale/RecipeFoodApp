package com.skl.foodrecipeapp.common.utils

sealed class Resource<T>(
    data: T? = null,
    error: String? = null
){
    class Success<T>(data: T): Resource<T>(data = data, error = null)
    class Failed<T>(error: String): Resource<T>(data = null, error = error)
    class Loading<T>(): Resource<T>()

}