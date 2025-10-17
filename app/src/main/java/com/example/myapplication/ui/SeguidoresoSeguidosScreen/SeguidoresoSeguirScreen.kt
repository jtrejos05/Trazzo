package com.example.myapplication.ui.SeguidoresoSeguidosScreen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

@Composable
fun SeguidoresoSeguidosScreen(modifier: Modifier){
    val state by viewmodel.uiState.collectAsState()
    LazyColumn(
        modifier = modifier
    ) {

    }
}