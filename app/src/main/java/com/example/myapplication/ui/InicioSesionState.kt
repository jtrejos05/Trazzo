package com.example.myapplication.ui

data class InicioSesionState(
    var correo: String="",
    var contraseña: String="",
    var recordarme: Boolean=false,
    var navegar: Boolean=false,
    var navegarRegister: Boolean=false,
    var mostrarError: Boolean=false,
    var error: String=""
)
