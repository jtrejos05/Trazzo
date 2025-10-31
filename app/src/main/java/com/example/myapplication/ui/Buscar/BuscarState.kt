package com.example.myapplication.ui.Buscar

import com.example.myapplication.data.Obra

data class BuscarState(
    val texto: String = "",
    val categoriasTop: List<String> = emptyList(),
    val trending: List<String> = emptyList(),
    val recientes: List<String> = emptyList(),
    val explora: List<String> = emptyList(),

    val navegarCategoria: Boolean = false,
    val categoriaSeleccionada: String = "",
    val navegarTrending: Boolean = false,
    val trendingSeleccionado: String = "",

    val resultadosBusqueda: List<Obra> = emptyList(),
    val errorMessage: String? = null,
    val isLoading: Boolean = false,

    val mensajeSinResultados: String? = null,

    var obras: List<Obra> = emptyList(),
)