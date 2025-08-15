package com.example.myapplication.data

import androidx.annotation.DrawableRes

data class Artista(
    @DrawableRes
    val img: Int,
    val correo: String,
    val contrasena: String,
    val usuario: String,
    val edad: Int,
    val profesion: String,
    val biografia: String
)
