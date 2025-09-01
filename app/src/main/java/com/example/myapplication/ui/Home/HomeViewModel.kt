package com.example.myapplication.ui.Home

import androidx.lifecycle.ViewModel
import com.example.myapplication.ui.Home.HomeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(HomeState())
    var uiState: MutableStateFlow<HomeState> = _uiState

    //funciones para cuando se opriman botones de login y register
    fun loginButtonPressed(){
        _uiState.update { it.copy(navegar = true) }
    }
    fun registerButtonPressed(){
        _uiState.update { it.copy(navegarRegister = true) }
    }
    //funcion para resetear las flags y evitar dobles llamados
    fun resetFlag(){
        _uiState.update { it.copy(navegar = false) }
        _uiState.update { it.copy(navegarRegister = false) }
    }
}