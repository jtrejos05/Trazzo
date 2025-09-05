package com.example.myapplication.ui.SubirObra

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.ui.SubirObra.SubirObraViewModel
import com.example.myapplication.ui.utils.Bienvenida
import com.example.myapplication.ui.utils.BotonInteres
import com.example.myapplication.ui.utils.Form
import com.example.myapplication.ui.utils.LogoTrazzo

//Pantalla Final Subir Obra
@Composable
fun SubirObraScreen(viewmodel: SubirObraViewModel,
                    ButtonPressed: () -> Unit = {},
                    modifier: Modifier = Modifier
){
    val state by viewmodel.uiState.collectAsState()
    Column(modifier=modifier.verticalScroll(rememberScrollState())) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxWidth()
        ) {   Spacer(modifier= Modifier.height(70.dp))
            LogoTrazzo(modifier=Modifier.height(70.dp))
            Bienvenida(R.string.sube_tu_obra, R.string.muestrale_al_mundo_tu_creacion)
        }
        //Empieza el Formulario para subir una obra
        Form(texto = stringResource(R.string.titulo), name= stringResource(R.string.dale_un_titulo_a_tu_video), dato = state.titulo,
             onChange = { viewmodel.updateTitulo(it) })
        Form(texto = stringResource(R.string.descripcion), name= stringResource(R.string.dale_una_descripcion), dato = state.descrpcion,
             onChange = { viewmodel.updateDescripcion(it) })
        Form(icon = Icons.Outlined.CameraAlt,description= stringResource(R.string.icono_camara),texto = stringResource(
            R.string.imagen
        ), name = stringResource(R.string.https_ejemplo_com_imagen_jpg),
            state.imagen,
            onChange = { viewmodel.updateImagen(it) },
            op=1)
        Text(stringResource(R.string.tambien_puedes_subir_desde_tu_galeria_tocando_el_icono_de_la_camara), modifier = Modifier.padding(vertical = 3.dp, horizontal = 18.dp))
        Form(texto = stringResource(R.string.categoria), name = stringResource(R.string.seleccionar), dato = state.categoria,
             onChange = { viewmodel.updateCategoria(it) }, op=1)
        Form(Icons.Default.Tag,
            stringResource(R.string.icono_hashtag),

            stringResource(R.string.tags),
            stringResource(R.string.acuarela_tecnica_retrato_arte_digital),state.tags, onChange = {viewmodel.updateTags(it)},op=1)
        //Termina el Formulario
        Text(stringResource(R.string.separa_los_tags_con_comas_para_ayudar_a_otros_a_encontrar_tu_contenido), modifier = Modifier.padding(vertical = 3.dp, horizontal = 18.dp))
        Spacer(modifier = Modifier.height(20.dp))
        BotonesFinal({ButtonPressed()})
        Spacer(modifier = Modifier.height(25.dp))
    }


}


@Composable
fun BotonesFinal(ButtonPressed: () -> Unit, modifier: Modifier = Modifier){
    Row(modifier = modifier.fillMaxWidth(1F),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically){
        BotonInteres(
            stringResource(R.string.cancelar), MaterialTheme.colorScheme.errorContainer, MaterialTheme.colorScheme.onSecondaryContainer, ButtonPressed,modifier
                .width(180.dp)
                .height(50.dp))
        Spacer(modifier= Modifier.width(20.dp))
        BotonInteres(
            stringResource(R.string.publicar), MaterialTheme.colorScheme.primaryContainer,MaterialTheme.colorScheme.onSecondaryContainer, ButtonPressed,modifier
                .width(180.dp)
                .height(50.dp) )
    }
}
@Composable
@Preview(showBackground = true)
fun BotonesFinalPreview(){
    BotonesFinal({})
}



