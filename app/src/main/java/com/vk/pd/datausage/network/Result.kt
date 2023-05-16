package com.vk.pd.datausage.network

sealed interface Result<out R> {
//    data class Success<out T>(val data: T) : Result<T>()
//    data class Error(val exception: Exception) : Result<Nothing>()

    data class Success<T>(val data: T) : Result<T>

    data class Error(val message: String) : Result<Nothing>

    object Loading : Result<Nothing>
}