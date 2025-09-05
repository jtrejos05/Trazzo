package com.example.myapplication.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class PublicacionesGuardadasViewModel @Inject constructor(): ViewModel() {
    private val _uiState = MutableStateFlow(PublicacionesGuardadasState())
    var uiState: MutableStateFlow<PublicacionesGuardadasState> = _uiState

    //FUncion para cuando se oprime una obra
    fun ObraPressed(int: Int = 0){
        _uiState.update { it.copy(navegar = true) }
        _uiState.update { it.copy(obra = int) }
    }
    //Funcion para resetear las flags y evitar dobles llamados
    fun resetFlag(){
        _uiState.update { it.copy(navegar = false) }
    }
}