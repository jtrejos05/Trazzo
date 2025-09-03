package com.example.myapplication.ui.Editar

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class EditarPerfilViewModel @Inject constructor(): ViewModel() {
    //Declaracion Variable state
    private val _uiState = MutableStateFlow(EditarPerfilState())
    var uiState: StateFlow<EditarPerfilState> = _uiState


    //funciones para actualizar los atributos de uiState
    fun updateUsuario(nuevoUsuario: String) {
        _uiState.update {it.copy(usuario = nuevoUsuario)  }
    }
    fun updateProfesion(nuevaProfesion: String) {
        _uiState.update {it.copy(profesion = nuevaProfesion)  }
    }
    fun updateBio(nuevaBio: String) {
        _uiState.update {it.copy(bio = nuevaBio)  }
    }
}