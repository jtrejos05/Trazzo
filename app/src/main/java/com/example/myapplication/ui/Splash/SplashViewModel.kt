package com.example.myapplication.ui.Splash

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel(){
    private val _uiState = MutableStateFlow(SplashState())
    var uiState: MutableStateFlow<SplashState> = _uiState

    private fun checkUser(){
        if (authRepository.currentUser != null){
            Log.d("Splash", "${authRepository.currentUser}")
            _uiState.update { it.copy(navegar = true) }
        }else{
            _uiState.update { it.copy(navegar = false) }
        }
    }
    fun resetFlag(){
        _uiState.update { it.copy(navegar = false) }
    }
    init {
        checkUser()
    }
}