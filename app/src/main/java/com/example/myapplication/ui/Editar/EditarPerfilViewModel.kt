package com.example.myapplication.ui.Editar

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.R
import com.example.myapplication.data.Artista
import com.example.myapplication.data.local.ProveedorObras
import com.example.myapplication.data.repository.StorageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EditarPerfilViewModel @Inject constructor(
    private val storage: StorageRepository
): ViewModel() {
    //Declaracion Variable state
    private val _uiState = MutableStateFlow(EditarPerfilState())
    var uiState: StateFlow<EditarPerfilState> = _uiState


    //funciones para actualizar los atributos de uiState
    fun updateUsuario(nuevoUsuario: String) {
        _uiState.update {it.copy(usuario = nuevoUsuario)  }
    }
    fun updateProfesion(nuevaProfesion: String) {
        _uiState.update {it.copy(profesion = nuevaProfesion)  }
    }
    fun updateBio(nuevaBio: String) {
        _uiState.update {it.copy(bio = nuevaBio)  }
    }
    //fun updateprofileImageUrl(profileImageUrl: Uri){
      //  _uiState.update {it.copy(profileImgUrl = profileImageUrl)  }
    //}
    fun uploadImageToFirebase(uri: Uri){
        viewModelScope.launch {
            val result = storage.uploadProfileImage(uri)
            if (result.isSuccess){
                _uiState.update { it.copy(profileImgUrl = result.getOrNull()) }
            }
        }
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
        //_uiState.update { it.copy(user = artista ) }
    }
    init {
        getUsuario()
    }
}