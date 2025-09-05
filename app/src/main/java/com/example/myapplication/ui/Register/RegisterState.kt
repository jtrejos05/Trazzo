package com.example.myapplication.ui.Register

data class RegisterState(
    var correo: String="" ,
    var contraseña: String="",
    var usuario: String="",
    var edad: String="",
    var profesion: String="",
    var bio: String="",
    var mostrarMensajeError: Boolean=false,
    var mensajeError: String = "",
    var navegar: Boolean = false
)