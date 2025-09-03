package com.example.myapplication.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


@HiltViewModel
class VistaObrasViewModel @Inject constructor(): ViewModel() {
    private val _uiState = MutableStateFlow(VistaObrasState())
    var uiState: MutableStateFlow<VistaObrasState> = _uiState

}