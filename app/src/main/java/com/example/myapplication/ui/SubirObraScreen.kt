package com.example.myapplication.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun SubirObraScreen(
    ButtonPressed: () -> Unit = {},
    modifier: Modifier = Modifier
){
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var imagen by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var tags by remember { mutableStateOf("") }


    Column(modifier=modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxWidth()
        ) {   Spacer(modifier= Modifier.height(70.dp))
            LogoTrazzo(modifier=Modifier.height(70.dp))
            Bienvenida(R.string.sube_tu_obra, R.string.muestrale_al_mundo_tu_creacion)
        }
        Form(texto = "Titulo", name="Dale un titulo a tu video", dato = titulo,
             onChange = { titulo = it })
        Form(texto = "Descripcion", name="Dale una descripcion", dato = descripcion,
             onChange = { descripcion = it })
        Form(icon = R.drawable.camara ,description="Icono Camara",texto = "Imagen", name = "https://ejemplo.com/imagen.jpg",
            imagen,
            onChange = { imagen = it },
            op=1)
        Text("Tambien puedes subir desde tu galeria tocando el icono de la camara", modifier = Modifier.padding(vertical = 3.dp, horizontal = 18.dp))
        Form(texto = "Categoria", name = "Seleccionar", dato = categoria,
             onChange = { categoria = it }, op=2)
        Form(R.drawable.hashtag,"Icono Hashtag","Tags", "acuarela, tecnica, retrato, arte-digital",tags, onChange = {tags=it},op=1)
        Text("Separa los tags con comas para ayudar a otros a encontrar tu contenido", modifier = Modifier.padding(vertical = 3.dp, horizontal = 18.dp))
        Spacer(modifier = Modifier.height(20.dp))
        BotonesFinal(ButtonPressed)
        Spacer(modifier = Modifier.height(25.dp))
    }


}
@Composable
@Preview(showBackground = true)
fun SubirObraPreview(){
    SubirObraScreen()
}

@Composable
fun BotonesFinal(ButtonPressed: () -> Unit, modifier: Modifier = Modifier){
    Row(modifier = modifier.fillMaxWidth(1F),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically){
        BotonInteres("Cancelar",R.color.rojo_enfasis,R.color.GrisClaro, ButtonPressed,modifier
            .width(180.dp)
            .height(50.dp))
        Spacer(modifier= Modifier.width(20.dp))
        BotonInteres("Publicar",R.color.MentaBri,R.color.GrisClaro, ButtonPressed,modifier
            .width(180.dp)
            .height(50.dp) )
    }
}
@Composable
@Preview(showBackground = true)
fun BotonesFinalPreview(){
    BotonesFinal({})
}



