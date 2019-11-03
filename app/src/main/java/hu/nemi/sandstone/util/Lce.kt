package hu.nemi.sandstone.util

sealed class Lce<out T> {
    object Loading : Lce<Nothing>()
    data class Error(val error: Throwable) : Lce<Nothing>()
    data class Content<T>(val value: T) : Lce<T>()
}