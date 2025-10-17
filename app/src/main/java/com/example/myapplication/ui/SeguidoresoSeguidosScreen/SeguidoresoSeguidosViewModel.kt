package com.example.myapplication.ui.SeguidoresoSeguidosScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.UserRepository
import com.example.myapplication.ui.Buscar.BuscarState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class SeguidoresoSeguidosViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(BuscarState())
    val uiState: StateFlow<BuscarState> = _uiState

    fun getUsers(seguidos: Boolean,id: String){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val result=userRepository.
                if (result.isSuccess){
                    _uiState.update { it.copy(obras = result.getOrDefault(emptyList())) }
                }else{
                    Log.e("PerfilViewModel", "Error al obtener usuario")
                    result.exceptionOrNull()?.printStackTrace()
                }
                //Error si la lista está vacía.
                if (_uiState.value.obras.isEmpty()) {
                    throw Exception("No se encontraron obras para mostrar. Por favor, revisa la fuente de datos.")
                }

                // Actualiza el estado con las obras si la carga es exitosa.
                _uiState.update { it.copy(isLoading = false) }

            } catch (e: Exception) {
                // Si ocurre un error, actualiza el estado con el mensaje de error.
                _uiState.update { it.copy(
                    isLoading = false,
                    errorMessage = "Error al cargar las obras: ${e.message}"
                ) }
            }
        }
    }
}