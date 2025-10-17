package com.example.myapplication.data

import androidx.annotation.DrawableRes

data class Artista(
    val id :String,
    var img: String,
    val correo: String,
    val contrasena: String,
    val usuario: String,
    val edad: Int,
    val profesion: String,
    val biografia: String,
    val seguidores: Int,
    val siguiendo: Int,
    val likes: Int,
    var obras: List<Obra>,
    val interses: List<String>,
    var seSiguen: Boolean = false
)
