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




    init {
        cargarSiguientes()
    }

    fun cargarSiguientes() {
        if (_uiState.value.isLoading) return
        _uiState.value.isLoading = true

        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                val result = ObrasRepo.getObrasPaginadas()
                if (result.isSuccess) {
                    val obras = result.getOrNull()?: emptyList()
                    if (!obras.isEmpty()){
                    _uiState.update {
                        it.copy(
                            obras = /*_uiState.value.obras +*/ obras,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                    }else{
                        _uiState.update { it.copy( final = true) }
                    }
                }else{
                    _uiState.update { it.copy(isLoading = false, errorMessage = result.exceptionOrNull()?.message) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            } finally {
                _uiState.value.isLoading = false
            }
        }
    }
}

