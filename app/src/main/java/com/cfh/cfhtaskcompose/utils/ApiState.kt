package com.cfh.cfhtaskcompose.utils

sealed class ApiState<out T> {
    object Idle: ApiState<Nothing>()
    object Loading: ApiState<Nothing>()
    data class Success<T>(val data: T): ApiState<T>()
    data class Error(val message: String): ApiState<Nothing>()

}