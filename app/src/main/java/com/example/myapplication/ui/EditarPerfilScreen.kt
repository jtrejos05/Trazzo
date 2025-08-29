package com.example.myapplication.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.utils.BotonInteres
import com.example.myapplication.ui.utils.Form
import com.example.myapplication.ui.utils.LogoTrazzo
//Pantalla para editar perfil
@Composable
fun EditarPerfilScreen(viewmodel: EditarPerfilViewModel,modifier: Modifier= Modifier) {

    val state by viewmodel.uiState.collectAsState()
    Column(modifier = modifier,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
    verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center) {
        Spacer(modifier= Modifier.height(70.dp))
        LogoTrazzo(modifier=Modifier.height(70.dp)
            .fillMaxWidth())
        Spacer(modifier = Modifier.height(10.dp))

        //Empieza el Formulario

        Form(texto = "Tu nombre de Usuario", name = "Dale un nombre creativo a tu perfil", dato = state.usuario,
             onChange = { viewmodel.updateUsuario(it) })
        Form(texto = "Tu Profesion", name = "Haz cambiado de profesion?", dato = state.profesion,
             onChange = { viewmodel.updateProfesion(it) })
        Form(texto = "Tu Bio", name = "Cuentanos mas de ti", dato = state.bio,
             onChange = { viewmodel.updateBio(it) })
        Spacer(modifier= Modifier.height(30.dp))

        BotonInteres("Guardar", MaterialTheme.colorScheme.primaryContainer,MaterialTheme.colorScheme.onSecondaryContainer, {},modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 16.dp))

    }
}
