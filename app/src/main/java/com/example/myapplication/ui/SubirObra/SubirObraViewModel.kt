package com.example.myapplication.ui.SubirObra

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.dtos.ArtistaObraDto
import com.example.myapplication.data.dtos.CreateObraDto
import com.example.myapplication.data.dtos.ObraDto
import com.example.myapplication.data.repository.AuthRepository
import com.example.myapplication.data.repository.ComentarioRepository
import com.example.myapplication.data.repository.ObraRepository
import com.example.myapplication.data.repository.StorageRepository
import com.example.myapplication.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class SubirObraViewModel @Inject constructor(
    private val obraRepo: ObraRepository,
    private val authRepository: AuthRepository,
    private val storageRepository: StorageRepository,
    private val userRepo: UserRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(SubirObraState())
    var uiState: StateFlow<SubirObraState> = _uiState



    //FUnciones para actualizar los campos de escritura
    fun updateTitulo(nuevoTitulo: String) {

        _uiState.update { it.copy(titulo = nuevoTitulo) }

    }
    fun updateDescripcion(nuevaDescripcion: String) {
        _uiState.update { it.copy(descrpcion = nuevaDescripcion) }
    }
    fun updateImagen(nuevaImagen: String) {
        _uiState.update { it.copy(imagen = nuevaImagen) }
    }
    fun updateCategoria(nuevaCategoria: String) {
        _uiState.update { it.copy(categoria = nuevaCategoria) }
    }
    fun updateTags(nuevosTags: String) {
        _uiState.update { it.copy(tags = nuevosTags) }
    }
    fun getUser(){
        viewModelScope.launch {
            Log.d("NuevaObra","USUARIO: ${authRepository.currentUser!!.uid}")
        val user = userRepo.getUserById(authRepository.currentUser!!.uid)
            Log.d("NuevaObra","Volvio")
            if (user.isSuccess) {
                Log.d("NuevaObra","USUARIO LLego")
                _uiState.update { it.copy(artista = user.getOrNull()) }
            }
        }
    }

    fun tags(): List<String> {
        val tags: List<String> = _uiState.value.tags.split(",")
        return tags
    }
    fun generatePostgresTimestamp(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z", Locale.getDefault())
        return formatter.format(Date())
    }
    fun createObra(id: String? = null){
        Log.d("NuevaObra","Entro")
        val userId = authRepository.currentUser?.uid
        Log.d("NuevaObra","$userId")
        if (userId != null) {
            val user = _uiState.value.artista
            Log.d("NuevaObra","${user == null}")
            val dto = ArtistaObraDto(user!!.usuario,user!!.img)
            val obra = CreateObraDto(id,userId,_uiState.value.uploadedImageUrl?:"",_uiState.value.titulo,_uiState.value.descrpcion,tags(),generatePostgresTimestamp(),generatePostgresTimestamp(),dto )
            Log.d("NuevaObra","Obra creada ${obra.id}, ${obra.artistaId}, ${obra.obraIMG}, ${obra.titulo}, ${obra.descripcion}, ${obra.Tags}")
            viewModelScope.launch {
                val result = obraRepo.createObra(obra)
                Log.d("NuevaObra","Repo")
                if (result.isSuccess) {
                    _uiState.update { it.copy(navigateBack = true) }
                } else {
                    Log.d("User", result.exceptionOrNull()?.message ?: "null")
                    _uiState.update { it.copy(error = result.exceptionOrNull()?.message) }
                }
            }
        }
    }

    fun updateImage(uri:Uri){
        _uiState.update { it.copy(uploadedImageUrl = uri.toString(), selectedImageUri = uri) }
    }
    fun uploadImageToFirebase(uri: Uri){
        viewModelScope.launch {
            Log.d("NuevaObra","Subiendo Obra")
            val result = storageRepository.uploadObraImage( authRepository.currentUser!!.uid,uri)
            if (result.isSuccess){
                Log.d("NuevaObra","Subida")
                _uiState.update { it.copy(uploadedImageUrl = result.getOrNull()) }
            }
        }
    }

init {
    getUser()
}






}