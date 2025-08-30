package com.example.myapplication.ui.Editar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.Editar.EditarPerfilViewModel
import com.example.myapplication.ui.utils.BotonInteres
import com.example.myapplication.ui.utils.Form
import com.example.myapplication.ui.utils.LogoTrazzo
//Pantalla para editar perfil
@Composable
fun EditarPerfilScreen(viewmodel: EditarPerfilViewModel, modifier: Modifier= Modifier) {

    val state by viewmodel.uiState.collectAsState()
    Column(modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center) {
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
