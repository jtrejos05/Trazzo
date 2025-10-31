package com.example.myapplication.ui.EditarPerfil

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.utils.BotonInteres
import com.example.myapplication.ui.utils.Form
import com.example.myapplication.ui.utils.LogoTrazzo
import com.example.myapplication.ui.utils.PickImageButton
import com.example.myapplication.ui.utils.profileAssyncImage


//Pantalla para editar perfil
@Composable
fun EditarPerfilScreen(id: String,viewmodel: EditarPerfilViewModel, modifier: Modifier= Modifier) {

    val state by viewmodel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        viewmodel.getUser(id)
        Log.d("UserEDIT","Se encontro user")
    }
    when{
        state.isLoading->{
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
                CircularProgressIndicator()
            }
        }

        state.errormsg != null ->{
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
                Text(state.errormsg ?: "Error desconocido")
            }
        }
        else ->{
            Column(modifier = modifier.verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                if (state.errormsg != null){
                    Text(state.errormsg!!)
                }else {
                    Spacer(modifier = Modifier.height(70.dp))
                    LogoTrazzo(
                        modifier = Modifier.height(70.dp)
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    profileAssyncImage(profileImage = state.profileImgUrl ?: "", size = 200)
                    PickImageButton(
                        action = {
                            viewmodel.uploadImageToFirebase(it)
                        }
                    )
                    Spacer(modifier = Modifier.height(50.dp))
                    Form(
                        texto = "Tu nombre de Usuario",
                        name = "Con que nombre quieres ser conocido",
                        dato = state.usuario,
                        onChange = { viewmodel.updateUsuario(it) },
                        modifier = Modifier.testTag("FormCambiarNombre")
                    )
                    Form(
                        texto = "Tu Profesion",
                        name = "cuentanos a que te dedicas",
                        dato = state.profesion,
                        onChange = { viewmodel.updateProfesion(it) },
                        modifier = Modifier.testTag("FormCambiarProfesion"))
                    Form(
                        texto = "Tu Bio", name = "Actualiza tu bio, que tienes para contarnos", dato = state.bio,
                        onChange = { viewmodel.updateBio(it) },
                        modifier = Modifier.testTag("FormCambiarBio"))
                    Spacer(modifier = Modifier.height(30.dp))

                    BotonInteres(
                        "Guardar",
                        MaterialTheme.colorScheme.primaryContainer,
                        MaterialTheme.colorScheme.onSecondaryContainer,
                        {viewmodel.updateUser()},
                        modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .padding(horizontal = 16.dp)
                            .testTag("BotonGuardar")
                    )
                }
        }
    }


    }
}
