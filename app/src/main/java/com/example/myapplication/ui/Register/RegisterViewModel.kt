package com.example.myapplication.ui.Register

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class RegisterViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(RegisterState())
    var uiState: MutableStateFlow<RegisterState> = _uiState

    //funciones para actualizar los campos de escribir

    fun updateCorreo(nuevoCorreo: String) {
        _uiState.update { it.copy(correo = nuevoCorreo) }
    }
    fun updateContraseña(nuevaContraseña: String) {
        _uiState.update { it.copy(contraseña = nuevaContraseña) }
    }
    fun updateUsuario(nuevoUsuario: String) {
        _uiState.update { it.copy(usuario = nuevoUsuario) }

    }
    fun updateEdad(nuevaEdad: String) {
        _uiState.update { it.copy(edad = nuevaEdad) }
    }
    fun updateProfesion(nuevaProfesion: String) {
        _uiState.update { it.copy(profesion = nuevaProfesion) }
    }
    fun updateBio(nuevaBio: String) {
        _uiState.update { it.copy(bio = nuevaBio) }
    }
}