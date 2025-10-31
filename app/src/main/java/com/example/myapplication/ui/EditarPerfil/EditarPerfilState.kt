package com.example.myapplication.ui.EditarPerfil

import com.example.myapplication.data.Artista

data class EditarPerfilState(
    var usuario:String = "",
    var profesion: String="",
    var bio: String="",
    var profileImgUrl: String? = null,
    var artista: Artista? = null,
    var errormsg: String? = "",
    var isLoading: Boolean = false,
    var navegar: Boolean = false
)