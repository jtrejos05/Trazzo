package com.example.myapplication.ui.Perfil

import androidx.lifecycle.ViewModel
import com.example.myapplication.R
import com.example.myapplication.data.Artista
import com.example.myapplication.data.local.ProveedorObras
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class PerfilViewModel @Inject constructor(): ViewModel() {

    private val _uiState = MutableStateFlow(PerfilState())
    var uiState: MutableStateFlow<PerfilState> = _uiState




    //FUncion para cambiar de tabs
    fun updateSelectedTab(nuevaSelectedTab: Int = 0) {
        _uiState.update { it.copy(selectedTab = nuevaSelectedTab) }
    }
    fun getUsuario(){
        val artista = Artista(
            img = R.drawable.maria_foto,
            correo = "Correo@gmail.com",
            contrasena = "123456",
            usuario = "Maria",
            edad = 20,
            profesion = "Artista",
            biografia = "Me encanta el arte y la pintura.",
            seguidores = 13,
            siguiendo = 1,
            likes = 20,
            obras = ProveedorObras.obras,
            interses = listOf("Pintura", "Escultura", "Fotografía")
        )
        _uiState.update { it.copy(usuario = artista ) }
    }

    init {
        getUsuario()
    }
}