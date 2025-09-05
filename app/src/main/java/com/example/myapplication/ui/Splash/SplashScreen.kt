package com.example.myapplication.ui.Splash

import android.window.SplashScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

@Composable
fun SplashScreen(
    navigateToPrincipal: ()-> Unit,
    navigateToInicio: ()-> Unit,
    splashViewModel: SplashViewModel,
    modifier: Modifier = Modifier
){
    val state by splashViewModel.uiState.collectAsState()

    if (state.navegar){
        navigateToPrincipal()
    }else{
        navigateToInicio()
    }
}