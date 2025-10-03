package com.example.myapplication.ui.Perfil

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.R
import com.example.myapplication.data.Artista
import com.example.myapplication.data.local.ProveedorActividad
import com.example.myapplication.data.local.ProveedorNotificaciones
import com.example.myapplication.data.local.ProveedorObras
import com.example.myapplication.data.repository.AuthRepository
import com.example.myapplication.data.repository.ComentarioRepository
import com.example.myapplication.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PerfilViewModel @Inject constructor(
    private  val authRepository: AuthRepository,
    private val comentarioRepo: ComentarioRepository,
    private val userRepo: UserRepository
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
    fun updateArtista(artista: Artista){
        _uiState.update { it.copy(usuario = artista) }
    }
    fun getUsuario(id: String){
        viewModelScope.launch {
            val result=userRepo.getUserById(id)
            if (result.isSuccess){
                _uiState.update { it.copy(usuario = result.getOrDefault(Artista("","","","","",0,"","",0,0,0,listOf(),listOf("")))) }
            }else{
                Log.e("PerfilViewModel", "Error al obtener usuario")
                result.exceptionOrNull()?.printStackTrace()
            }
        }


    }
    fun getReviews(id:Int){
        viewModelScope.launch {
            val result = comentarioRepo.getComentarioByArtistaId(_uiState.value.usuario.id)
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
        getNotificaciones()

    }
}