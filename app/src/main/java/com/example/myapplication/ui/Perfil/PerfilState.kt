package com.example.myapplication.ui.Perfil

import com.example.myapplication.data.ActividadItem
import com.example.myapplication.data.Artista
import com.example.myapplication.data.NotificacionItem

data class PerfilState(
    var selectedTab: Int = 0,
    var usuario:Artista = Artista(0,"","","",0,"","",0,0,0,listOf(),listOf("")),
    var actividades: List<ActividadItem> = emptyList(),
    var notificaciones: List<NotificacionItem> = emptyList()
)