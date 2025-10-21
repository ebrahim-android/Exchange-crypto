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

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repo: DetailRepository
) : ViewModel() {

    private val _state = MutableStateFlow<UiState<CryptoDetail>>(UiState.Loading)
    val uiState: StateFlow<UiState<CryptoDetail>> = _state

    // --- Upload the complete detail with graphic included ---
    fun loadCryptoDetail(id: String, days: String) {
        viewModelScope.launch {
            _state.value = UiState.Loading
            try {
                val result = repo.getCryptoDetail(id, days)
                Log.d("DetailViewModel", "Detalle cargado: ${result.history.size} puntos ($days días)")
                _state.value = UiState.Success(result)
            } catch (e: Exception) {
                Log.e("DetailViewModel", "Error cargando detalle", e)
                _state.value = UiState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}
