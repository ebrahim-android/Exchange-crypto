package com.practica.exchangecrypto.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practica.exchangecrypto.domain.model.CryptoModel
import com.practica.exchangecrypto.domain.repository.CryptoRepository
import com.practica.exchangecrypto.domain.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// Marks this ViewModel for Hilt dependency injection.
@HiltViewModel
class HomeViewModel @Inject constructor(
    // Injects the repository dependency via the constructor.
    private val repo: CryptoRepository
) : ViewModel() {

    // MutableStateFlow holds the current state of the UI (Loading, Success, or Error).
    // It's initialized to 'Loading'. The data payload is a list of CryptoModel.
    private val _uiState = MutableStateFlow<UiState<List<CryptoModel>>>(UiState.Loading)

    // Public, immutable StateFlow wrapper for the UI to observe changes.
    val uiState: StateFlow<UiState<List<CryptoModel>>> = _uiState.asStateFlow()

    // ------------------- Pagination State -------------------

    // Tracks the current page number for API requests. Starts at 1.
    private var currentPage = 1

    // Defines the number of items requested per page from the API.
    private val perPage = 50

    // Holds the accumulated list of cryptocurrencies loaded so far from all pages.
    private var aggregated = mutableListOf<CryptoModel>()

    // Initializes data loading when the ViewModel is created.
    init { loadMarkets() }

    /**
     * Fetches cryptocurrency market data from the repository.
     * This function handles initial loading, pagination, and state updates.
     *
     * @param page The page number to fetch. Defaults to 1 (initial load/refresh).
     */
    fun loadMarkets(page: Int = 1){
        // Launches a coroutine in the ViewModel's scope, ensuring it is cancelled when the ViewModel is cleared.
        viewModelScope.launch {

            // Check if this is the first page request (or a refresh).
            if (page == 1) {
                // Set the state to Loading to display a progress indicator.
                _uiState.value = UiState.Loading
                // Clear the aggregated list before starting a fresh load.
                aggregated.clear()
            }

            try {
                // Call the repository to fetch the data for the requested page.
                val list = repo.getMarkets(vsCurrency = "usd", page = page, perPage = perPage)

                // Check if the request returned results.
                if (list.isNotEmpty()) {
                    // Add the newly fetched list to the aggregated collection.
                    aggregated.addAll(list)
                    // Update the UI state with the full, accumulated list of data.
                    _uiState.value = UiState.Success(aggregated.toList())
                } else {
                    // Handles the case where the API returns an empty list (e.g., reaching the last page).
                    // We still update the UI state with the current data, just no new items were added.
                    _uiState.value = UiState.Success(aggregated.toList())
                }
            } catch (e: Exception) {
                // Catches network or parsing errors and updates the UI state to Error.
                _uiState.value = UiState.Error(e.message ?: "unknown error")
            }
        }
    }

    /**
     * Resets the pagination and reloads the first page of data.
     * Typically called when the user initiates a manual refresh (e.g., Pull-to-Refresh).
     */
    fun refresh() {
        currentPage = 1
        loadMarkets(1)
    }

    /**
     * Increments the page counter and requests the next page of market data.
     * Called by the Fragment when the user scrolls near the end of the list (Infinite Scrolling).
     */
    fun loadNextPage() {
        currentPage += 1
        loadMarkets(currentPage)
    }
}