package com.example.myapplication.ui.Editar

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.R
import com.example.myapplication.data.Artista
import com.example.myapplication.data.local.ProveedorObras
import com.example.myapplication.data.repository.AuthRepository
import com.example.myapplication.data.repository.StorageRepository
import com.example.myapplication.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EditarPerfilViewModel @Inject constructor(
    private val storage: StorageRepository,
    private val authRepository: AuthRepository,
    private val userRepo: UserRepository
): ViewModel() {
    //Declaracion Variable state
    private val _uiState = MutableStateFlow(EditarPerfilState(profileImgUrl = authRepository.currentUser?.photoUrl?.toString() ?: ""))
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
    fun getUser(id:String){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errormsg = null) }
            val result = userRepo.getUserById(id)
            if (result.isSuccess) {
                _uiState.update {
                    it.copy(
                        artista = result.getOrDefault(
                            Artista(
                                "",
                                "",
                                "",
                                "",
                                "",
                                0,
                                "",
                                "",
                                0,
                                0,
                                0,
                                listOf(),
                                listOf("")
                            )
                        )
                    )
                }
                _uiState.update { it.copy(isLoading = false, errormsg = null) }
                Log.d("USER", uiState.value.artista!!.usuario)
                _uiState.update { it.copy(usuario = _uiState.value.artista!!.usuario, profesion =_uiState.value.artista!!.profesion, bio = _uiState.value.artista!!.biografia) }
            } else {
                _uiState.update { it.copy(isLoading = false, errormsg = result.exceptionOrNull()?.message) }
                Log.e("PerfilViewModel", "Error al obtener usuario")

            }
        }
    }
    fun uploadImageToFirebase(uri: Uri){
        viewModelScope.launch {
            val result = storage.uploadProfileImage(uri)
            if (result.isSuccess){
                _uiState.update { it.copy(profileImgUrl = result.getOrNull()) }
            }
        }
    }

    fun updateUser(){
        viewModelScope.launch {
            userRepo.updateArtista(usuario = _uiState.value.usuario, edad = _uiState.value.artista!!.edad.toString(), profesion = _uiState.value.profesion, bio = _uiState.value.bio, fotous = _uiState.value.profileImgUrl!!, userId =_uiState.value.artista!!.id )
            _uiState.update { it.copy(navegar = true) }
        }
    }

    fun resetFlag() {
        _uiState.update { it.copy(navegar = false) }

    }


}