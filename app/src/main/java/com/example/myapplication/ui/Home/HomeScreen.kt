package com.example.myapplication.ui.Home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.ui.Home.HomeViewModel
import com.example.myapplication.ui.utils.AddButton
import com.example.myapplication.ui.utils.LogoTrazzo

//Mensaje de bienvenida
@Composable
fun MensajeBienvenida(
    nombre: String,
    modifier: Modifier = Modifier
){
    Text(
        text = stringResource(R.string.bienvenido_a)+" "+nombre,
        fontSize = 20.sp, // sp (para texto) y dp
        fontWeight = FontWeight.Bold,
        modifier = modifier
        )
}
//Para pintar el logo
@Composable
fun ExternalLogo(
    idImagen: Int,
    description: String,
    modifier: Modifier = Modifier
){
    Image(
        painter = painterResource(id = idImagen),
        contentDescription = description,
        modifier = modifier
            .padding(8.dp)
            .height(48.dp)
            .width(48.dp)
    )
}

// Body del home Screen
@Composable
fun BodyHomeScreen(
    loginButtonPressed: () -> Unit,
    registerButtonPressed: () -> Unit,
    modifier: Modifier = Modifier
){
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        // Logo de Trazzo
        LogoTrazzo()

        MensajeBienvenida(stringResource(R.string.trazzo))
        Spacer(modifier = Modifier.height(23.dp))
        AddButton(
            stringResource(R.string.iniciar_sesion),
            onClick = {loginButtonPressed()},
            modifier = Modifier.width(200.dp)
        )
        AddButton(
            stringResource(R.string.registrarse),
            onClick = {registerButtonPressed()},
            modifier = Modifier.width(200.dp),
        )

        Row {
            ExternalLogo(idImagen = R.drawable.google_logo, description = "Google")
            ExternalLogo(idImagen = R.drawable.facebook_logo, description = "Facebook")
            ExternalLogo(idImagen = R.drawable.instagram_logo, description = "Instagram")
            ExternalLogo(idImagen = R.drawable.github_logo, description = "Github")
        }
    }
}
//Pantalla final Home
@Composable
fun HomeScreen(
    loginButtonPressed: () -> Unit,
    registerButtonPressed: () -> Unit,
    modifier: Modifier = Modifier
){
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ){
        Spacer(modifier = Modifier.weight(1f))
        BodyHomeScreen(loginButtonPressed,registerButtonPressed)
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = stringResource(R.string.todos_los_derechos_reservados)
        )
    }
}


