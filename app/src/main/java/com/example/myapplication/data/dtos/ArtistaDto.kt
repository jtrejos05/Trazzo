package com.example.myapplication.data.dtos

data class ArtistaDto(
    val id: Int,
    val nombre: String,
    val correo: String,
    val fotousuario: String,
    val contraseña: String,
    val edad: Int,
    val profesion: String,
    val biografia: String,
    val seguidores: Int,
    val seguidos: Int,
    val likes: Int,
    val createdAt: String,
    val updatedAt: String
)
