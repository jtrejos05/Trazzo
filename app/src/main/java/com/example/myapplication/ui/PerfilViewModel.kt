package com.example.myapplication.ui

import android.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class PerfilViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(PerfilState())
    var uiState: MutableStateFlow<PerfilState> = _uiState


    //Funcion para resetear las flags y evitar dobles llamados
    fun resetFlag() {
        _uiState.update { it.copy(navegar = false) }
        _uiState.update { it.copy(guardar = false) }
        _uiState.update { it.copy(editar = false) }

    }
    //Funciones para cuando se oprime un boton
    fun guardadoPressed(){
        _uiState.update { it.copy(guardar = true) }
    }
    fun ObraPressed(int: Int = 0){
        _uiState.update { it.copy(navegar = true) }
        _uiState.update { it.copy(obra = int) }

    }
    fun EditarPressed(){
        _uiState.update { it.copy(editar = true) }
    }
    //FUncion para cambiar de tabs
    fun updateSelectedTab(nuevaSelectedTab: Int = 0) {
        _uiState.update { it.copy(selectedTab = nuevaSelectedTab) }
    }
}