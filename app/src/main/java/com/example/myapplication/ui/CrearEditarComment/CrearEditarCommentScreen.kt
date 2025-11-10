package com.example.myapplication.ui.CrearEditarComment

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.ui.utils.BotonInteres
import com.example.myapplication.ui.utils.Form
import com.example.myapplication.ui.utils.PickImageButton
import com.example.myapplication.ui.utils.obraAssyncImage
import com.example.myapplication.ui.utils.profileAssyncImage
import kotlin.math.roundToInt

@Composable
fun CrearEditarScreen(
    id: String? = null,
    obraId: String,
    responseId: String? = null,
    onBack: ()-> Unit,
    viewModel: CrearEditarCommentViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
){
    val state by viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        if (id != null){
            Log.d("IDESITAR","$id")
            viewModel.getCommentById(id)
        }
    }

    LaunchedEffect(state.navigateBack) {
        if (state.navigateBack){
            onBack()
        }
    }

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier= Modifier.height(70.dp))
        Form(Icons.Default.Edit,"Icono Editar", "Deja tu comentario aqui","Deja al autor conocer tu opinion",
            state.comment,{viewModel.onCommentChange(it)}
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text("Calificacion: ${state.calificacion}")
        Slider(
            value = state.calificacion.toFloat(),
            onValueChange = {
                val rounded = (it * 2).roundToInt() / 2.0
                viewModel.onCalificacionChange(rounded) },
            valueRange = 0f..5f,
            steps = 9,
            modifier = Modifier.width(350.dp)
        )
        if (state.error != ""){
            Text(state.error!!, color = MaterialTheme.colorScheme.error)
        }
        Spacer(modifier= Modifier.height(20.dp))
        Row {
            BotonInteres("Cancelar", MaterialTheme.colorScheme.errorContainer, MaterialTheme.colorScheme.onSecondaryContainer, {},modifier
                .width(180.dp)
                .height(50.dp))
            Spacer(modifier= Modifier.width(20.dp))
            BotonInteres("Publicar", MaterialTheme.colorScheme.primaryContainer,MaterialTheme.colorScheme.onSecondaryContainer,
                {
                    Log.d("IDESITAR crear", "Obra${state.obraId} ID$id")
                    if (state.obraId == ""){
                        viewModel.createComment(obraId= obraId, parentComment = responseId,id= id)
                    }else{
                        viewModel.createComment(obraId= state.obraId, parentComment = responseId,id= id)
                    }

                },modifier
                    .width(180.dp)
                    .height(50.dp) )

        }
        obraAssyncImage(Image = state.uploadedImageUrl ?: "", size = 200)
        PickImageButton(
            action={
                viewModel.updateImage(it)
            }
        )




    }
}