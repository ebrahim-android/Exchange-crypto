package com.practica.exchangecrypto.ui.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practica.exchangecrypto.data.repository.DetailRepository
import com.practica.exchangecrypto.domain.model.CryptoDetail
import com.practica.exchangecrypto.domain.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// Marks this ViewModel for Hilt dependency injection.
@HiltViewModel
class DetailViewModel @Inject constructor(
    // Inject the DetailRepository dependency.
    private val repo: DetailRepository
) : ViewModel() {

    // Mutable StateFlow to hold the current UI state (Loading, Success, or Error)
    // for the detailed crypto data.
    private val _state = MutableStateFlow<UiState<CryptoDetail>>(UiState.Loading)
    // Public immutable StateFlow for the Fragment to observe.
    val uiState: StateFlow<UiState<CryptoDetail>> = _state

    /**
     * Fetches the complete cryptocurrency detail, including historical price data for the chart.
     *
     * @param id The unique ID of the cryptocurrency.
     * @param days The time range (e.g., "7", "30") for the chart data.
     */
    fun loadCryptoDetail(id: String, days: String) {
        // Launch a coroutine in the ViewModel's scope.
        viewModelScope.launch {
            _state.value = UiState.Loading // Set state to loading before network request.
            try {
                // Fetch the detailed data, including price history.
                val result = repo.getCryptoDetail(id, days)
                Log.d("DetailViewModel", "Detail loaded: ${result.history.size} points ($days days)")
                // Set state to success with the fetched data.
                _state.value = UiState.Success(result)
            } catch (e: Exception) {
                Log.e("DetailViewModel", "Error loading detail", e)
                // Set state to error upon failure.
                _state.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}