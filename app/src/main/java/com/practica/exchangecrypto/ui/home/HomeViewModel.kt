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

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: CryptoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<CryptoModel>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<CryptoModel>>> = _uiState.asStateFlow()

    //I don't know, it's something about how many cryptocurrencies are on the screen.
    private var currentPage = 1
    private val perPage = 50
    private var aggregated = mutableListOf<CryptoModel>()

    init { loadMarkets() }

    //I no idea, I gotta study this late
    fun loadMarkets(page: Int = 1){
        viewModelScope.launch {
            if (page == 1) {
                _uiState.value = UiState.Loading
                aggregated.clear()
            }
            try {
                val list = repo.getMarkets(vsCurrency = "usd", page = page, perPage = perPage)
                if (list.isNotEmpty()) {
                    aggregated.addAll(list)
                    _uiState.value = UiState.Success(aggregated.toList())
                } else {
                    // empty result (could be last page)
                    _uiState.value = UiState.Success(aggregated.toList())
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "unknown error")
            }
        }
    }

    fun refresh() { //to refresh the recycler view
        currentPage = 1
        loadMarkets(1)
    }

    fun loadNextPage() { //go on to the new page and load more crypto
        currentPage += 1
        loadMarkets(currentPage)
    }

}