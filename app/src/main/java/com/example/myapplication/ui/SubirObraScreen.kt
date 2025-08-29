package com.example.myapplication.ui

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.ui.utils.Bienvenida
import com.example.myapplication.ui.utils.BotonInteres
import com.example.myapplication.ui.utils.Form
import com.example.myapplication.ui.utils.LogoTrazzo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

//Pantalla Final Subir Obra
@Composable
fun SubirObraScreen(viewmodel: SubirObraViewModel,
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
        Form(texto = "Titulo", name="Dale un titulo a tu video", dato = state.titulo,
             onChange = { viewmodel.updateTitulo(it) })
        Form(texto = "Descripcion", name="Dale una descripcion", dato = state.descrpcion,
             onChange = { viewmodel.updateDescripcion(it) })
        Form(icon = Icons.Outlined.CameraAlt,description="Icono Camara",texto = "Imagen", name = "https://ejemplo.com/imagen.jpg",
            state.imagen,
            onChange = { viewmodel.updateImagen(it) },
            op=1)
        Text("Tambien puedes subir desde tu galeria tocando el icono de la camara", modifier = Modifier.padding(vertical = 3.dp, horizontal = 18.dp))
        Form(texto = "Categoria", name = "Seleccionar", dato = state.categoria,
             onChange = { viewmodel.updateCategoria(it) }, op=1)
        Form(Icons.Default.Tag,"Icono Hashtag","Tags", "acuarela, tecnica, retrato, arte-digital",state.tags, onChange = {viewmodel.updateTags(it)},op=1)
        //Termina el Formulario
        Text("Separa los tags con comas para ayudar a otros a encontrar tu contenido", modifier = Modifier.padding(vertical = 3.dp, horizontal = 18.dp))
        Spacer(modifier = Modifier.height(20.dp))
        BotonesFinal({viewmodel.ButtonPressed()})
        Spacer(modifier = Modifier.height(25.dp))
    }


}


@Composable
fun BotonesFinal(ButtonPressed: () -> Unit, modifier: Modifier = Modifier){
    Row(modifier = modifier.fillMaxWidth(1F),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically){
        BotonInteres("Cancelar", MaterialTheme.colorScheme.errorContainer, MaterialTheme.colorScheme.onSecondaryContainer, ButtonPressed,modifier
            .width(180.dp)
            .height(50.dp))
        Spacer(modifier= Modifier.width(20.dp))
        BotonInteres("Publicar", MaterialTheme.colorScheme.primaryContainer,MaterialTheme.colorScheme.onSecondaryContainer, ButtonPressed,modifier
            .width(180.dp)
            .height(50.dp) )
    }
}
@Composable
@Preview(showBackground = true)
fun BotonesFinalPreview(){
    BotonesFinal({})
}



