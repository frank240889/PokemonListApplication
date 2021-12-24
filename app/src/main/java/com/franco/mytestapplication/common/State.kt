package com.franco.mytestapplication.common

/**
 * A class to represent states.
 */
sealed class State<out T>{
    data class Success<out T>(val data: T): State<T>()
    data class Error(val error: Throwable): State<Nothing>()
    object Loading: State<Nothing>()
}
