package com.example.myapplication.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class BuscarViewModel:ViewModel() {
    //Variable State para el view model
    private val _uiState = MutableStateFlow(BuscarState())
   var uiState: StateFlow<BuscarState> = _uiState
    fun updateTexto(nuevoTexto: String) {
        _uiState.update{it.copy(texto = nuevoTexto)}
    }
}