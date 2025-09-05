package com.example.myapplication.ui.InicioSesion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class InicioSesionViewModel @Inject constructor(
    private  val authRepository: AuthRepository
): ViewModel() {



    private val _uiState = MutableStateFlow(InicioSesionState())
    var uiState: StateFlow<InicioSesionState> = _uiState

    //Funcion para resetear las flags y evitar dobles llamados
    fun resetFlag(){
        _uiState.update { it.copy(navegar = false) }

    }
    //funciones pra cuando se oprimen botones
    fun loginButtonPressed(){
        if (_uiState.value.correo.isNullOrEmpty()||_uiState.value.contraseña.isNullOrEmpty()) {
            _uiState.update { it.copy(mostrarError = true, error = "Por favor, complete todos los campos") }

        }else {
            if (_uiState.value.contraseña.length < 6) {
                _uiState.update { it.copy(mostrarError = true, error = "La contraseña debe tener al menos 6 caracteres") }
            }else{
                viewModelScope.launch {
                    try {
                        authRepository.signIn(_uiState.value.correo,_uiState.value.contraseña)
                        _uiState.update { it.copy(navegar = true) }
                    }catch (e: Exception){
                        _uiState.update { it.copy(mostrarError = true) }
                        _uiState.update { it.copy(error = "Error al iniciar sesion") }
                    }
                }


            }
        }
    }
    //Funciones para actualizaciones de campos de texto
    fun updateCorreo(nuevoCorreo: String) {
        _uiState.update { it.copy(correo = nuevoCorreo) }
    }
    fun updateContraseña(nuevaContraseña: String) {
        _uiState.update { it.copy(contraseña = nuevaContraseña) }
    }
    fun updateRecordarme(nuevoRecordarme: Boolean) {
        _uiState.update { it.copy(recordarme = nuevoRecordarme) }
    }
}