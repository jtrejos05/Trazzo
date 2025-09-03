package com.example.myapplication.ui.SubirObra

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class SubirObraViewModel @Inject constructor(): ViewModel() {

    private val _uiState = MutableStateFlow(SubirObraState())
    var uiState: StateFlow<SubirObraState> = _uiState



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