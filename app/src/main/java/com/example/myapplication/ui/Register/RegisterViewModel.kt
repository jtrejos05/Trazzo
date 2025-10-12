package com.example.myapplication.ui.Register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.AuthRepository
import com.example.myapplication.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class RegisterViewModel @Inject constructor(
    private  val authRepository: AuthRepository,
    private val userRepository: UserRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(RegisterState())
    var uiState: MutableStateFlow<RegisterState> = _uiState

    fun registerButtonPressed() {
        if (_uiState.value.contraseña.isNullOrEmpty() || _uiState.value.correo.isNullOrEmpty() || _uiState.value.usuario.isNullOrEmpty() || _uiState.value.edad.isNullOrEmpty()) {
            _uiState.update { it.copy(mostrarMensajeError = true) }
            _uiState.update { it.copy(mensajeError = "Debes llenar al menos los campos: Correo, Contraseña, Usuario y Edad ") }
        } else {
            if (_uiState.value.contraseña.length < 6) {
                _uiState.update { it.copy(mostrarMensajeError = true) }
                _uiState.update { it.copy(mensajeError = "La contraseña debe tener al menos 6 caracteres ") }
            } else {
                if (_uiState.value.correo == "admin@admin.com") {
                    _uiState.update { it.copy(mostrarMensajeError = true) }
                    _uiState.update { it.copy(mensajeError = "Ese correo ya esta en uso ") }
                } else {
                    viewModelScope.launch {

                        val result = authRepository.signUp(_uiState.value.correo, _uiState.value.contraseña)
                        if (result.isSuccess) {
                            Log.d("USER", "Va a ver userId")

                            val userId= authRepository.currentUser?.uid ?: throw Exception("No se encontro el usuario")

                            Log.d("USER", "RViewModel ${userId}")

                            userRepository.registerUser(
                                usuario = uiState.value.usuario,
                                edad = uiState.value.edad,
                                profesion = uiState.value.profesion,
                                bio = uiState.value.bio,
                                userId = userId,
                            )

                            _uiState.update { it.copy(navegar = true) }
                        } else {
                            val mensaje =
                                result.exceptionOrNull()?.message ?: "Error al iniciar Sesion"
                            _uiState.update { it.copy(mostrarMensajeError = true) }
                            _uiState.update { it.copy(mensajeError = mensaje) }
                        }


                    }

                }
            }
        }
    }
        //funcion para resetear flags
        fun resetFlag() {
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
