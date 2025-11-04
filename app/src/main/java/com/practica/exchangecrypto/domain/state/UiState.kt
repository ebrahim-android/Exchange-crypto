package com.practica.exchangecrypto.domain.state

// Sealed class used to represent and control the state of the User Interface (UI).
// This ensures that all possible states (Loading, Success, Error) are handled explicitly.
sealed class UiState<out T> {

    // Represents the state when data is being fetched or processed.
    object Loading: UiState<Nothing>()

    // Represents the successful completion of an operation, carrying the resulting data.
    data class Success<T>(val data: T) : UiState<T>()

    // Represents an error state, carrying an explanatory message.
    data class Error(val message: String) : UiState<Nothing>()
}