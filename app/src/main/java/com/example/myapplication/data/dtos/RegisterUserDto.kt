package com.example.myapplication.data.dtos

data class RegisterUserDto(
    val nombre: String,
    val edad : String,
    val profesion:String,
    val biografia: String,
    var id: String,
    var fotousuario: String?,
    val numSeguidores: Int = 0,
    val numSeguidos: Int = 0,
    val FCMToken: String? = null

)
