package com.example.myapplication.data.dtos

data class RegisterUserDto(
    val nombre: String,
    val edad : String,
    val profesion:String,
    val biografia: String,
    var id: String,
    var fotousuario: String?

)
