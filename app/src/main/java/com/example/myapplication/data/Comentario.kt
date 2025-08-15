package com.example.myapplication.data

import androidx.annotation.DrawableRes

data class Comentario(
    @DrawableRes val fotous: Int,
    val usuario: String,
    val hora: String,
    val comentario: String,
    val likes: Int
)
