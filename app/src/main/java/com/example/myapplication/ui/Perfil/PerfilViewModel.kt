package com.example.myapplication.ui.Perfil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.R
import com.example.myapplication.data.Artista
import com.example.myapplication.data.local.ProveedorActividad
import com.example.myapplication.data.local.ProveedorNotificaciones
import com.example.myapplication.data.local.ProveedorObras
import com.example.myapplication.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PerfilViewModel @Inject constructor(
    private  val authRepository: AuthRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(PerfilState())
    var uiState: MutableStateFlow<PerfilState> = _uiState



    fun onOutClick(){
        authRepository.signOut()
    }

    //FUncion para cambiar de tabs
    fun updateSelectedTab(nuevaSelectedTab: Int = 0) {
        _uiState.update { it.copy(selectedTab = nuevaSelectedTab) }
    }
    fun getUsuario(){
        val artista = Artista(
            img = R.drawable.maria_foto,
            correo = "Correo@gmail.com",
            contrasena = "123456",
            usuario = "LA K",
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
    fun getActividades(){
        _uiState.update { it.copy(actividades = ProveedorActividad.actividades) }
    }
    fun getNotificaciones(){
        _uiState.update { it.copy(notificaciones = ProveedorNotificaciones.notificaciones) }
    }

    init {
        getUsuario()
        getNotificaciones()
        getActividades()
    }
}