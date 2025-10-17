package com.example.myapplication.ui.Principal

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Artista
import com.example.myapplication.data.local.ProveedorObras
import com.example.myapplication.data.repository.ObraRepository
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrincipalViewModel @Inject constructor(
    private val ObrasRepo: ObraRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(PrincipalState())
    var uiState: MutableStateFlow<PrincipalState> = _uiState

    //FUnción para cuando se selecciona una obra.
    fun getObras(){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            ObrasRepo.listeAllObras().catch { e-> _uiState.update { it.copy(errorMessage = e.message, isLoading = false) } }
                .collect { obras ->
                    _uiState.update { it.copy(obras= obras,isLoading = false, errorMessage = null) }
                }
        }
    }
    init {
        getObras()
    }
}

