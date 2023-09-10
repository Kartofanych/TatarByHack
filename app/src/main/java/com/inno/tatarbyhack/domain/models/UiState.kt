package com.inno.tatarbyhack.domain.models

sealed interface UiState<out T>{
    object Start:UiState<Nothing>
    data class Success<T>(val result: T):UiState<T>
    data class Error(val cause: String):UiState<Nothing>
}