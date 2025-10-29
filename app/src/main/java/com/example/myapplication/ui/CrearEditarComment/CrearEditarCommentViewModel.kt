package com.example.myapplication.ui.CrearEditarComment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.AuthRepository
import com.example.myapplication.data.repository.ComentarioRepository
import com.example.myapplication.ui.Buscar.BuscarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.jvm.Throws

@HiltViewModel
class CrearEditarCommentViewModel @Inject constructor(
    private val commentRepo: ComentarioRepository,
    private val authRepository: AuthRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(CrearEditarCommentState())
    val uiState: StateFlow<CrearEditarCommentState> = _uiState

    fun onCommentChange(comment:String){
        _uiState.update { it.copy(comment = comment) }
    }
    fun onCalificacionChange(calificacion:Double){
        _uiState.update { it.copy(calificacion=calificacion) }
    }

    fun createComment(obraId: String,parentComment: String? = null, id: String? = null){
        val userId = authRepository.currentUser?.uid
        if (userId != null) {
            if (_uiState.value.comment.isEmpty()){
                _uiState.update { it.copy(error = "Por favor, escriba un comentario") }
                return
            }
            viewModelScope.launch {
                val result = commentRepo.createComentario(
                    _uiState.value.comment,
                    _uiState.value.calificacion,
                    userId,
                    obraId,
                    parentComment,
                    id
                )
                if (result.isSuccess) {
                    _uiState.update { it.copy(navigateBack = true) }
                } else {
                    Log.d("User", result.exceptionOrNull()?.message ?: "null")
                    _uiState.update { it.copy(error = result.exceptionOrNull()?.message) }
                }
            }
        }
    }

    fun getCommentById(id: String){
        viewModelScope.launch {
            val result = commentRepo.getComenarioById(id)
            if (result.isSuccess){
                val comment = result.getOrNull()
                if (comment != null){
                    _uiState.update { it.copy(comment = comment.comentario, calificacion = comment.calificacion, obraId = comment.obraId) }
                }
            }
        }
    }


}