package com.example.myapplication.data

import androidx.annotation.DrawableRes

data class Review(@DrawableRes val fotous: Int,
                  val usuario: String,
                  val hora: String,
                  val calificacion: Int,
                  val comentario: String)
