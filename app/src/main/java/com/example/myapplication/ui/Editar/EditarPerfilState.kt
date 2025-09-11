package com.example.myapplication.ui.Editar

import com.example.myapplication.data.Artista

data class EditarPerfilState(
    var usuario:String = "",
    var profesion: String="",
    var bio: String="",
    var profileImgUrl: String? = null
)