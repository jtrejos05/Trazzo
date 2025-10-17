package com.example.myapplication.ui.SeguidoresoSeguidosScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.UserRepository
import com.example.myapplication.ui.Buscar.BuscarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeguidoresoSeguidosViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(SeguidoresoSeguidosState())
    val uiState: StateFlow<SeguidoresoSeguidosState> = _uiState

    fun getUsers(seguidos: Boolean,id: String){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val result=userRepository.getSeguidosOrSeguidores(seguidos,id)
                if (result.isSuccess){
                    _uiState.update { it.copy(lista = result.getOrDefault(emptyList())) }
                }else{

                    result.exceptionOrNull()?.printStackTrace()
                }
                //Error si la lista está vacía.
                if (_uiState.value.lista.isEmpty()) {
                    throw Exception("No se encontraron usuarios para mostrar. Por favor, revisa la fuente de datos.")
                }


                _uiState.update { it.copy(isLoading = false) }

            } catch (e: Exception) {

                _uiState.update { it.copy(
                    isLoading = false,
                    errorMessage = "Error al cargar usuarios: ${e.message}"
                ) }
            }
        }
    }
}