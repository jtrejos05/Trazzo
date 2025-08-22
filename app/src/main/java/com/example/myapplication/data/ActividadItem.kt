package com.example.myapplication.data
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class ActividadItem(
    val icon: ImageVector,
    val descripcion: String,
    val tiempo: String,
    val color: Color
 )