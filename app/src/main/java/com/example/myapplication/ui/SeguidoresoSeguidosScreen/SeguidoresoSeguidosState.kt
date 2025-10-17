package com.example.myapplication.ui.SeguidoresoSeguidosScreen

import com.example.myapplication.data.Artista

data class SeguidoresoSeguidosState(
    val lista:List<Artista> = emptyList(),
    var isLoading: Boolean = false,
    var errorMessage: String? = null
)
