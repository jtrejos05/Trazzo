package com.example.myapplication.ui.utils

data class Publicacion(
    val id: Int,
    val hora: String,
    val username: String,
    val titulo: String,
    val descripcion: String,
    val categorias: List<String>,
    val likes: String,
    val comentarios: String,
    val compartidos: String,
    val idPerfil: Int,
    val idImagen: Int? = null
)

