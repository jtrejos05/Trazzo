package com.example.myapplication.data.dtos

data class CreateObraDto(
    val id: String,
    val artistaId: String,
    val obraIMG: String,
    val titulo: String,
    val descripcion: String,
)
