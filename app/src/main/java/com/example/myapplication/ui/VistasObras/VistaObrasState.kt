package com.example.myapplication.ui.VistasObras

import com.example.myapplication.data.Comentario
import com.example.myapplication.data.Obra

data class VistaObrasState(
    var obra: Obra? = null,
    var isLoading: Boolean = false,
    var errorMessage: String? = null,
    var reviews: List<Comentario> = emptyList(),
    var currentUser: String=""
)