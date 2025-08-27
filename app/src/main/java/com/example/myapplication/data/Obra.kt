package com.example.myapplication.data

import androidx.annotation.DrawableRes

data class Obra(
    @DrawableRes val fotous: Int,
    val usuario: String,
    val hora: String,
    val titulo: String,
    val descripcion: String,
    val Tags: List<String>,
    @DrawableRes val foto: Int,
    val likes: String,
    val comentarios: String,
    val compartidos: String,
    val vistas: String = "0",
    val obraId: Int
)
