package com.example.myapplication.ui.Perfil

import com.example.myapplication.data.ActividadItem
import com.example.myapplication.data.Artista
import com.example.myapplication.data.Comentario
import com.example.myapplication.data.NotificacionItem

data class PerfilState(
    var selectedTab: Int = 0,
    var usuario:Artista = Artista("","","","","",0,"","",0,0,0,listOf(),listOf("")),
    var reviews: List<Comentario> = emptyList(),
    var notificaciones: List<NotificacionItem> = emptyList(),
    var errormsg: String? = ""
)