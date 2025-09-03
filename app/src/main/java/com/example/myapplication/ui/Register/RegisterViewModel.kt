package com.example.myapplication.ui.Register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class RegisterViewModel @Inject constructor(
    private  val authRepository: AuthRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(RegisterState())
    var uiState: MutableStateFlow<RegisterState> = _uiState

    fun registerButtonPressed(){
        if (_uiState.value.contraseña.isNullOrEmpty() ||_uiState.value.correo.isNullOrEmpty() ||_uiState.value.usuario.isNullOrEmpty() ||_uiState.value.edad.isNullOrEmpty()){
           _uiState.update { it.copy(mostrarMensajeError = true) }
           _uiState.update { it.copy(mensajeError = "Debes llenar al menos los campos: Correo, Contraseña, Usuario y Edad ") }
        }else{
            if (_uiState.value.contraseña.length<6){
               _uiState.update { it.copy(mostrarMensajeError = true) }
               _uiState.update { it.copy(mensajeError = "La contraseña debe tener al menos 6 caracteres ") }
            }else{
                if (_uiState.value.correo == "admin@admin.com"){
                   _uiState.update { it.copy(mostrarMensajeError = true) }
                   _uiState.update { it.copy(mensajeError = "Ese correo ya esta en uso ") }
                }
                else{
                    viewModelScope.launch {
                        try {
                            authRepository.signUp(_uiState.value.correo,_uiState.value.contraseña)
                           _uiState.update { it.copy(navegar = true) }
                        }catch (e: Exception){
                           _uiState.update { it.copy(mostrarMensajeError = true) }
                           _uiState.update { it.copy(mensajeError = e.message.toString()) }
                        }

                    }


                }

            }
        }
    }
    //funcion para resetear flags
    fun resetFlag(){
        _uiState.update { it.copy(navegar = false) }

    }
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