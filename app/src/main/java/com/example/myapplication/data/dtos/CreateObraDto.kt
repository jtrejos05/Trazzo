package com.example.myapplication.data.dtos

data class CreateObraDto(
    val id: Int,
    val artistaId: Int,
    val obraIMG: String,
    val titulo: String,
    val descripcion: String,
)
