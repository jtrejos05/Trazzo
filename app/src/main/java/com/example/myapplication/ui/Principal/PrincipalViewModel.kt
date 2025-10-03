package com.example.myapplication.ui.Principal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.local.ProveedorObras
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrincipalViewModel @Inject constructor(): ViewModel() {
    private val _uiState = MutableStateFlow(PrincipalState())
    var uiState: MutableStateFlow<PrincipalState> = _uiState

    //FUnción para cuando se selecciona una obra.
    fun getObras(){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                // Simulación de carga de datos.
                val obras = ProveedorObras.obras

                //Error si la lista está vacía.
                if (obras.isEmpty()) {
                    throw Exception("No se encontraron obras para mostrar. Por favor, revisa la fuente de datos.")
                }

                // Actualiza el estado con las obras si la carga es exitosa.
                _uiState.update { it.copy(obras = obras, isLoading = false) }

            } catch (e: Exception) {
                // Si ocurre un error, actualiza el estado con el mensaje de error.
                _uiState.update { it.copy(
                    isLoading = false,
                    errorMessage = "Error al cargar las obras: ${e.message}"
                ) }
            }
        }
    }
    init {
        getObras()
    }
}

