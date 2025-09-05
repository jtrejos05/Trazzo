package com.example.myapplication.ui.Principal

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.local.ProveedorObras
import com.example.myapplication.ui.Principal.PricipalState
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PrincipalViewModel @Inject constructor(): ViewModel() {
    private val _uiState = MutableStateFlow(PricipalState())
    var uiState: MutableStateFlow<PricipalState> = _uiState
    //FUncion para cuando se oprime una obra



    fun getObras(){
        _uiState.update { it.copy(obras = ProveedorObras.obras) }
    }
    init {
        getObras()
    }
}

