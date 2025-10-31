package com.example.myapplication.ui.Splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.AuthRepository
import com.example.myapplication.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel(){
    private val _uiState = MutableStateFlow(SplashState())
    var uiState: MutableStateFlow<SplashState> = _uiState


    /*private fun checkUser(){
        if (authRepository.currentUser != null){
            Log.d("Splash", "${authRepository.currentUser}")
            _uiState.update { it.copy(navegar = true) }
        }else{
            _uiState.update { it.copy(navegar = false) }
        }
    }*/
    private fun checkUser(){
        val currentUser = authRepository.currentUser

        if (currentUser != null){
            Log.d("Splash", "Usuario autenticado: ${currentUser.uid}")

            // ✅ Verificar que el usuario también exista en Firestore
            viewModelScope.launch {
                try {
                    val result = userRepository.getUserById(currentUser.uid)

                    if (result.isSuccess) {
                        val usuario = result.getOrNull()

                        if (usuario != null && usuario.id.isNotEmpty()) {
                            // ✅ Usuario existe en Auth Y Firestore
                            Log.d("Splash", "Usuario encontrado en Firestore")
                            _uiState.update { it.copy(navegar = true) }
                        } else {
                            // ❌ Usuario en Auth pero NO en Firestore
                            Log.w("Splash", "Usuario no encontrado en Firestore, cerrando sesión")
                            authRepository.signOut()
                            _uiState.update { it.copy(navegar = false) }
                        }
                    } else {
                        // ❌ Error al verificar Firestore
                        Log.e("Splash", "Error al verificar usuario en Firestore")
                        authRepository.signOut()
                        _uiState.update { it.copy(navegar = false) }
                    }
                } catch (e: Exception) {
                    Log.e("Splash", "Excepción al verificar usuario: ${e.message}")
                    authRepository.signOut()
                    _uiState.update { it.copy(navegar = false) }
                }
            }
        } else {
            // No hay usuario autenticado
            Log.d("Splash", "No hay usuario autenticado")
            _uiState.update { it.copy(navegar = false) }
        }
    }
    fun resetFlag(){
        _uiState.update { it.copy(navegar = false) }
    }
    init {
        checkUser()
    }
}