package com.example.myapplication.ui.Buscar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Obra
import com.example.myapplication.data.local.ProveedorObras
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.FlowPreview
import kotlin.text.lowercase

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


        viewModelScope.launch {
            uiState
                .map { it.texto }
                .debounce(500L)
                .distinctUntilChanged()
                .collect { query ->
                    if (query.isBlank()) {
                        _uiState.update { it.copy(resultadosBusqueda = emptyList(), mensajeSinResultados = null) }
                    } else {
                        realizarBusqueda(query)
                    }
                }
        }
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


    /*private fun simularBusqueda(query: String): List<Obra> {
        return ProveedorObras.obras
    }*/

    private fun simularBusqueda(query: String): List<Obra> {
        val queryMinusculas = query.lowercase().trim()
        if (queryMinusculas.isBlank()) {
            return emptyList()
        }

        return ProveedorObras.obras.filter { obra ->
            // 1. En el título de la obra
            val enTitulo = obra.titulo.lowercase().contains(queryMinusculas)
            // 2. En el nombre de usuario
            val enUsuario = obra.usuario.lowercase().contains(queryMinusculas)
            // 3. En la descripción
            val enDescripcion = obra.descripcion.lowercase().contains(queryMinusculas)
            // 4. En el ID del artista
            val enArtistaId = obra.artistaId.lowercase().contains(queryMinusculas)
            // 5. En alguna de las etiquetas (Tags)
            val enTags = obra.Tags.any { tag ->
                tag.lowercase().contains(queryMinusculas)
            }
            // La obra se incluye si cualquiera de las condiciones anteriores es verdadera
            enTitulo || enUsuario || enDescripcion || enArtistaId || enTags
        }
    }
}

