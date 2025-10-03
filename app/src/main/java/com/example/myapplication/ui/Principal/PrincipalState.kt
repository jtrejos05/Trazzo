package com.example.myapplication.ui.Principal

import com.example.myapplication.data.Obra

data class PrincipalState(
    var obras: List<Obra> = emptyList(),
    var errorMessage: String? = null,
    var isLoading: Boolean = false,
)