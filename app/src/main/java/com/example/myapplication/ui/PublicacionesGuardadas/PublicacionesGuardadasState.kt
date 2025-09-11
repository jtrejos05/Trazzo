package com.example.myapplication.ui.PublicacionesGuardadas

import com.example.myapplication.data.Obra

data class PublicacionesGuardadasState(
    var navegar: Boolean= false,
    var obras: List<Obra> = emptyList(),
    var errorMessage: String? = null,
    var isLoading: Boolean = false
)