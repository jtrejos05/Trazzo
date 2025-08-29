package com.example.myapplication.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class VistaObrasViewModel {
    private val _uiState = MutableStateFlow(VistaObrasState())
    var uiState: MutableStateFlow<VistaObrasState> = _uiState
}