package com.example.myapplication.ui.PublicacionesGuardadas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.local.ProveedorObras
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PublicacionesGuardadasViewModel @Inject constructor(): ViewModel() {
    private val _uiState = MutableStateFlow(PublicacionesGuardadasState())
    var uiState: MutableStateFlow<PublicacionesGuardadasState> = _uiState


    fun getObras(){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                // Simulación de datos.
                val obras = ProveedorObras.obras

                // Simulación escenario de error (sin publicaciones guardadas).
                if (obras.isEmpty()) {
                    throw Exception("No se encontraron publicaciones guardadas.")
                }

                // Actualización en caso de éxito.
                _uiState.update { it.copy(obras = obras, isLoading = false) }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Error al cargar las publicaciones: ${e.message}"
                    )
                }
            }
        }
    }
    init {
        getObras()
    }
}