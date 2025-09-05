package com.example.myapplication.ui.PublicacionesGuardadas

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.local.ProveedorObras
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PublicacionesGuardadasViewModel @Inject constructor(): ViewModel() {
    private val _uiState = MutableStateFlow(PublicacionesGuardadasState())
    var uiState: MutableStateFlow<PublicacionesGuardadasState> = _uiState


    fun getObras(){
        _uiState.update { it.copy(obras = ProveedorObras.obras) }
    }
    init {
        getObras()
    }
}