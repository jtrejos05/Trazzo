package com.example.myapplication.ui.Buscar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Obra
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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


    fun realizarBusqueda(query: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, mensajeSinResultados = null, errorMessage = null) }
            try {
                // Simulación de búsqueda en una fuente de datos.
                val resultados = simularBusqueda(query)

                if (resultados.isEmpty()) {
                    // Caso 1: No hay resultados
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            resultadosBusqueda = emptyList(),
                            mensajeSinResultados = "No se encontraron resultados para '$query'."
                        )
                    }
                } else {
                    // Caso 2: Búsqueda exitosa
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            resultadosBusqueda = resultados,
                            mensajeSinResultados = null
                        )
                    }
                }
            } catch (e: Exception) {
                // Caso 3: Error al buscar
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Hubo un problema al realizar la búsqueda. Por favor, inténtalo de nuevo."
                    )
                }
            }
        }
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


    private fun simularBusqueda(query: String): List<Obra> {
        return if (query.isNotEmpty() && query.lowercase().contains("arte")) {
            listOf(
                Obra(
                    fotous = "url_foto_usuario_1",
                    usuario = "Usuario1",
                    hora = "1h",
                    titulo = "Arte Moderno",
                    descripcion = "Una descripción de arte moderno.",
                    Tags = listOf("arte", "moderno"),
                    foto = "url_foto_obra_1",
                    likes = "100",
                    comentarios = "15",
                    compartidos = "5",
                    obraId = 1
                ),
                Obra(
                    fotous = "url_foto_usuario_2",
                    usuario = "Usuario2",
                    hora = "2h",
                    titulo = "Arte Contemporáneo",
                    descripcion = "Una descripción de arte contemporáneo.",
                    Tags = listOf("arte", "contemporaneo"),
                    foto = "url_foto_obra_2",
                    likes = "200",
                    comentarios = "25",
                    compartidos = "10",
                    obraId = 2
                ),
                Obra(
                    fotous = "url_foto_usuario_3",
                    usuario = "Usuario3",
                    hora = "3h",
                    titulo = "Arte Digital",
                    descripcion = "Una descripción de arte digital.",
                    Tags = listOf("arte", "digital"),
                    foto = "url_foto_obra_3",
                    likes = "300",
                    comentarios = "30",
                    compartidos = "15",
                    obraId = 3
                )
            )
        } else {
            emptyList()
        }
    }

}

