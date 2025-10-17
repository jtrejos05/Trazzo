package com.example.myapplication.data

import androidx.annotation.DrawableRes

data class Comentario(
    val id: String,
    val fotous: String,
    val usuario: String,
    val hora: String,
    val comentario: String,
    val likes: String,
    val calificacion: Double,
    val obraId: String,
    val liked: Boolean = false
)
