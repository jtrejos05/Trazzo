package com.example.myapplication.data

import androidx.annotation.DrawableRes

data class Obra(
    val fotous: String,
    val usuario: String,
    val hora: String,
    val titulo: String,
    val descripcion: String,
    val Tags: List<String>,
    val foto: String,
    val likes: String,
    val comentarios: String,
    val compartidos: String,
    val vistas: String = "0",
    val obraId: String
)
