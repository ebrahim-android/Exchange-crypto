package com.practica.exchangecrypto.domain.state

//sealed to control state of Ui
sealed class UiState<out T> {
    object Loading: UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}