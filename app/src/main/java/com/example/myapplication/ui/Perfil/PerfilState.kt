package com.example.myapplication.ui.Perfil

import com.example.myapplication.data.Artista

data class PerfilState(
    var selectedTab: Int = 0,
    var usuario:Artista = Artista(0,"","","",0,"","",0,0,0,listOf(),listOf(""))
)