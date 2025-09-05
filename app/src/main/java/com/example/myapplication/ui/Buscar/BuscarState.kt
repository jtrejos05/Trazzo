package com.example.myapplication.ui.Buscar

data class BuscarState(
    val texto: String = "",
    val categoriasTop: List<String> = emptyList(),
    val trending: List<String> = emptyList(),
    val recientes: List<String> = emptyList(),
    val explora: List<String> = emptyList(),

    val navegarCategoria: Boolean = false,
    val categoriaSeleccionada: String = "",
    val navegarTrending: Boolean = false,
    val trendingSeleccionado: String = ""
)