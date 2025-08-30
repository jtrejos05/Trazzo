package com.example.myapplication.ui.Trending

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.local.ProveedorObras
import com.example.myapplication.ui.PricipalState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class TrendingViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(TrendingState())
    var uiState: MutableStateFlow<TrendingState> = _uiState

    fun getObras(){
        _uiState.update { it.copy(obras = ProveedorObras.obras) }
    }
    init {
        getObras()
    }
}