package com.example.myapplication.ui.CrearEditarComment

data class CrearEditarCommentState(
    val comment:String = "",
    val calificacion: Double = 0.0,
    val navigateBack: Boolean = false,
    val error: String? = "",
    val obraId: String = ""
)
