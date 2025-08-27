package com.example.myapplication.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.ui.utils.AddButton
import com.example.myapplication.ui.utils.LogoTrazzo

@Composable
fun MensajeBienvenida(
    nombre: String,
    modifier: Modifier = Modifier
){
    Text(
        text = stringResource(R.string.bienvenido_a)+" "+nombre,
        fontSize = 20.sp, // sp (para texto) y dp
        fontWeight = FontWeight.Bold,
        color = colorResource(id = R.color.gris_texto_principal),
        modifier = modifier
        )
}

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

// Contenedores Row, Column, Box
@Composable
fun BodyHomeScreen(
    loginButtonPressed: () -> Unit = {},
    registerButtonPressed: () -> Unit = {},
    modifier: Modifier = Modifier
){
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        LogoTrazzo()
        MensajeBienvenida(stringResource(R.string.trazzo))
        Spacer(modifier = Modifier.height(23.dp))
        AddButton(stringResource(R.string.iniciar_sesion),onClick = loginButtonPressed)
        AddButton(stringResource(R.string.registrarse), onClick = registerButtonPressed)
        Row {
            ExternalLogo(idImagen = R.drawable.google_logo, description = "Google")
            ExternalLogo(idImagen = R.drawable.facebook_logo, description = "Facebook")
            ExternalLogo(idImagen = R.drawable.instagram_logo, description = "Instagram")
            ExternalLogo(idImagen = R.drawable.github_logo, description = "Github")
        }
    }
}

@Composable
fun HomeScreen(
    loginButtonPressed: () -> Unit = {},
    registerButtonPressed: () -> Unit = {},
    modifier: Modifier = Modifier
){
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ){
        Spacer(modifier = Modifier.weight(1f))
        BodyHomeScreen(loginButtonPressed = loginButtonPressed,
        registerButtonPressed = registerButtonPressed)
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = stringResource(R.string.todos_los_derechos_reservados),
            color = colorResource(id = R.color.gris_texto_secundario)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview(){
    HomeScreen()
}