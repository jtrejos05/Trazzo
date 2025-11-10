package com.example.myapplication.ui.CrearEditarComment

import android.net.Uri

data class CrearEditarCommentState(
    val comment:String = "",
    val calificacion: Double = 0.0,
    val navigateBack: Boolean = false,
    val error: String? = "",
    val obraId: String = "",
    var selectedImageUri: Uri? = null,
    val isUploadingImage: Boolean = false,
    var uploadedImageUrl: String? = null
)
