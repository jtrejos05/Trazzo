package com.example.myapplication.ui.Buscar

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BuscarViewModel @Inject constructor(): ViewModel() {

    private val _uiState = MutableStateFlow(BuscarState())
    val uiState: StateFlow<BuscarState> = _uiState

    init {
        // listas inicializadas
        _uiState.value = BuscarState(
            categoriasTop = listOf("Inicio", "Diseño digital", "Fotografía", "Pintura"),
            trending = listOf(
                "Concept Art",
                "Ilustración",
                "Arte tradicional",
                "Street Art",
                "Fotografía",
                "Collage"
            ),
            recientes = listOf("Fotografía", "Pintura", "Acuarela"),
            explora = listOf("Dibujo", "Origami", "Moda", "Escultura")
        )
    }

    fun updateTexto(nuevoTexto: String) {
        _uiState.update { it.copy(texto = nuevoTexto) }
    }

    fun seleccionarCategoria(categoria: String) {
        _uiState.update { it.copy(categoriaSeleccionada = categoria, navegarCategoria = true) }
    }

    fun resetFlagCategoria() {
        _uiState.update { it.copy(navegarCategoria = false) }
    }

    fun seleccionarTrending(trending: String) {
        _uiState.update { it.copy(trendingSeleccionado = trending, navegarTrending = true) }
    }

    fun resetFlagTrending() {
        _uiState.update { it.copy(navegarTrending = false) }
    }
}