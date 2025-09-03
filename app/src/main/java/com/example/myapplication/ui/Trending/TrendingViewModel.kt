package com.example.myapplication.ui.Trending

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.local.ProveedorObras
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class TrendingViewModel @Inject constructor(): ViewModel() {
    private val _uiState = MutableStateFlow(TrendingState())
    var uiState: MutableStateFlow<TrendingState> = _uiState

    fun getObras(){
        _uiState.update { it.copy(obras = ProveedorObras.obras) }
    }
    init {
        getObras()
    }
}