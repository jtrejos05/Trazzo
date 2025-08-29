package com.example.myapplication.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class SubirObraViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(SubirObraState())
    var uiState: StateFlow<SubirObraState> = _uiState


    //Funcion para cuando se oprime un boton
    fun ButtonPressed(){
        _uiState.update { it.copy(navegar = true) }

    }
    //Funcion para resetear las flags y evitar dobles llamados
    fun resetFlag(){
        _uiState.update { it.copy(navegar = false) }

    }


    //FUnciones para actualizar los campos de escritura
    fun updateTitulo(nuevoTitulo: String) {

        _uiState.update { it.copy(titulo = nuevoTitulo) }

    }
    fun updateDescripcion(nuevaDescripcion: String) {
        _uiState.update { it.copy(descrpcion = nuevaDescripcion) }
    }
    fun updateImagen(nuevaImagen: String) {
        _uiState.update { it.copy(imagen = nuevaImagen) }
    }
    fun updateCategoria(nuevaCategoria: String) {
        _uiState.update { it.copy(categoria = nuevaCategoria) }
    }
    fun updateTags(nuevosTags: String) {
        _uiState.update { it.copy(tags = nuevosTags) }
    }










}