package com.example.myapplication.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.data.local.ProveedorBotonesDeNavegacion.botones
import com.example.myapplication.ui.utils.LogoTrazzo
import com.example.myapplication.ui.utils.NavigationButton

@Composable
fun TopNavigationBar() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        // Fila superior (Logo, botones)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Logo
            LogoTrazzo(
                modifier = Modifier
                    .size(32.dp)
            )

            // Botones de sesión
            TextButton(onClick = { /* Acción iniciar sesión */ }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Login,
                    contentDescription = "Iniciar Sesión",
                    modifier = Modifier.size(18.dp),
                    tint = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(stringResource(R.string.iniciar_sesion), color = MaterialTheme.colorScheme.onBackground)
            }
            TextButton(onClick = { /* Acción registrarse */ }) {
                Icon(
                    imageVector = Icons.Filled.PersonAdd,
                    contentDescription = "Registrarse",
                    modifier = Modifier.size(18.dp),
                    tint = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(stringResource(R.string.registrarse), color = MaterialTheme.colorScheme.onBackground)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Barra de búsqueda
        OutlinedTextField(
            value = "",
            onValueChange = {},
            placeholder = { Text(stringResource(R.string.buscar_inspiraci_n_art_stica)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Barra inferior (Inicio, Trending, Origami, Moda)
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(botones.size){
                val boton = botones[it]
                NavigationButton(
                    text = boton.text,
                    icon = boton.icon,
                    selected = boton.selected
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun TopNavigationBarPreview() {
    TopNavigationBar()
}


