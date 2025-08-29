package com.example.myapplication.ui

data class PerfilState(
    var selectedTab: Int = 0,
    var navegar: Boolean=false,
    var guardar: Boolean= false,
    var editar: Boolean=false,
    var obra: Int=0
)
