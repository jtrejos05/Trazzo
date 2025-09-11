package com.example.myapplication.ui.Editar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ImageNotSupported
import androidx.compose.material.icons.filled.Loop
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myapplication.R
import com.example.myapplication.ui.Editar.EditarPerfilViewModel
import com.example.myapplication.ui.utils.BotonInteres
import com.example.myapplication.ui.utils.Form
import com.example.myapplication.ui.utils.LogoTrazzo
import com.example.myapplication.ui.utils.PickImageButton
import kotlin.contracts.contract



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
        Spacer(modifier = Modifier.height(30.dp))
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(state.profileImgUrl)
                .crossfade(true)
                .build(),
            error = painterResource(R.drawable.nocamera),
            placeholder = painterResource(R.drawable.loading),
            contentDescription = "User Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(200.dp).clip(CircleShape)
        )
        PickImageButton(
            action = {
                viewmodel.uploadImageToFirebase(it)
            }
        )
        Spacer(modifier= Modifier.height(50.dp))
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
