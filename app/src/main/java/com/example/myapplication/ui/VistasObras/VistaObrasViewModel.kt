package com.example.myapplication.ui.VistasObras

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Obra
import com.example.myapplication.data.local.ProveedorObras
import com.example.myapplication.data.repository.AuthRepository
import com.example.myapplication.data.repository.ComentarioRepository
import com.example.myapplication.data.repository.ObraRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VistaObrasViewModel @Inject constructor(
    private val ObrasRepo: ObraRepository,
    private val CommentRepo : ComentarioRepository,
    private val authRepository: AuthRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(VistaObrasState())
    var uiState: MutableStateFlow<VistaObrasState> = _uiState
    fun getObra(id: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val result=ObrasRepo.getObra(id,authRepository.currentUser?.uid ?: "")
                if (result.isSuccess){
                    _uiState.update { it.copy(obra = result.getOrNull()) }
                }else{
                    Log.e("PerfilViewModel", "Error al obtener usuario")
                    result.exceptionOrNull()?.printStackTrace()
                }
                //Error si la lista está vacía.
                if (_uiState.value.obra == null) {
                    throw Exception("No se encontro la obra para mostrar. Por favor, revisa la fuente de datos.")
                }else{
                    // Actualiza el estado con las obras si la carga es exitosa.
                    _uiState.update { it.copy(isLoading = false) }
                    getReviews()
                }
            } catch (e: Exception) {
                // Si ocurre un error, actualiza el estado con el mensaje de error.
                _uiState.update { it.copy(
                    isLoading = false,
                    errorMessage = "Error al cargar las obras: ${e.message}"
                ) }
            }
        }



    }
    fun getReviews(){
        viewModelScope.launch {
            try {
                Log.d("COMENTS",_uiState.value.obra!!.obraId)
                val result= CommentRepo.getComentarioByObraId(_uiState.value.obra!!.obraId)
                if (result.isSuccess){
                    _uiState.update { it.copy(reviews = result.getOrDefault(emptyList())) }
                }else{
                    Log.e("PerfilViewModel", "Error al obtener reviews")
                    result.exceptionOrNull()?.printStackTrace()
                }

            } catch (e: Exception) {
                // Si ocurre un error, actualiza el estado con el mensaje de error.
                _uiState.update { it.copy(
                    errorMessage = "Error al cargar los Reviews: ${e.message}"
                ) }
            }
        }
    }
    fun updateObra(obra: Obra?){
        _uiState.update { it.copy(obra=obra) }
    }

    fun sendOrDeleteLike(obraId: String, userId: String){
        viewModelScope.launch {
            val result = ObrasRepo.sendOrDeleteLike(obraId = obraId, userId = userId)
            if (result.isSuccess){

                _uiState.update { it.copy(
                    obra = it.obra?.copy(
                        liked = !it.obra!!.liked,
                        likes = if (it.obra!!.liked) (it.obra!!.likes.toInt()-1).toString() else (it.obra!!.likes.toInt()+1).toString()
                    )
                ) }

            }
        }

    }
    fun sendOrDeleteLikeComment(commentId:String,userId: String){
        viewModelScope.launch {
            val result = CommentRepo.sendOrDeleteLike(commentId = commentId, userId = userId)
            if (result.isSuccess){
                _uiState.update { it.copy(reviews = it.reviews.map { c-> if (c.id==commentId){
                        c.copy(
                            liked = !c.liked,
                            likes = if(c.liked)(c.likes.toInt()-1).toString() else (c.likes.toInt()+1).toString()
                        )
                    }else c
                }) }
            }
        }
    }

    init {
        _uiState.update { it.copy(currentUser = authRepository.currentUser?.uid ?: "") }
    }

}