package com.example.myapplication.ui.VistasObras

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.Obra
import com.example.myapplication.data.local.ProveedorObras
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class VistaObrasViewModel @Inject constructor(): ViewModel() {
    private val _uiState = MutableStateFlow(VistaObrasState())
    var uiState: MutableStateFlow<VistaObrasState> = _uiState
    fun getObra(id: Int): Obra? {
        //ir al repo y filtrar
        val obra = ProveedorObras.obras.find { it.obraId == id }

            return obra


    }
    fun updateObra(obra: Obra?){
        _uiState.update { it.copy(obra=obra) }
    }


}