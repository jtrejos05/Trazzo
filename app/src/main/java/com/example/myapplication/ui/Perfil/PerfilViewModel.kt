package com.example.myapplication.ui.Perfil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.R
import com.example.myapplication.data.Artista
import com.example.myapplication.data.local.ProveedorActividad
import com.example.myapplication.data.local.ProveedorNotificaciones
import com.example.myapplication.data.local.ProveedorObras
import com.example.myapplication.data.repository.AuthRepository
import com.example.myapplication.data.repository.ComentarioRepository
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PerfilViewModel @Inject constructor(
    private  val authRepository: AuthRepository,
    private val comentarioRepo: ComentarioRepository
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
            img = authRepository.currentUser?.photoUrl.toString(),
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
            interses = listOf("Pintura", "Escultura", "Fotografía"),
            id = "1"
        )
        _uiState.update { it.copy(usuario = artista ) }
    }
    fun getReviews(){
        viewModelScope.launch {
            val result = comentarioRepo.getComentarioByArtistaId("1")
            if (result.isSuccess){
                _uiState.update { it.copy(reviews = result.getOrNull() ?: emptyList()) }

            }else{
                result.exceptionOrNull()?.printStackTrace()
            }

        }

    }
    fun getNotificaciones(){
        _uiState.update { it.copy(notificaciones = ProveedorNotificaciones.notificaciones) }
    }

    init {
        getUsuario()
        getNotificaciones()
        getReviews()
    }
}