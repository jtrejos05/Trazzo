package com.example.myapplication.ui.SubirObra

import android.net.Uri
import com.example.myapplication.data.Artista

data class SubirObraState(
    var titulo: String="",
    var descrpcion: String="",
    var imagen: String="",
    var categoria: String="",
    var tags: String="",
    var selectedImageUri: Uri? = null,
    val isUploadingImage: Boolean = false,
    var uploadedImageUrl: String? = null,
    val navigateBack: Boolean = false,
    var error: String? = null,
    var artista: Artista? = null

)