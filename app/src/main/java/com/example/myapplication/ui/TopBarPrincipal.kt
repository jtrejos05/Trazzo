package com.example.myapplication.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.ui.utils.LogoTrazzo


private val botones = listOf(
    "Inicio" to Icons.Default.Home,
    "Trending" to Icons.Default.Star,
    "Origami" to Icons.Default.Create,
    "Moda" to Icons.Default.Favorite
)

@Composable
fun TopBarPrincipal(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        // Fila superior logo
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            LogoTrazzo(
                modifier = Modifier.size(32.dp)
            )
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
            items(botones) { boton ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(onClick = { /* TODO acción */ }) {
                        Icon(
                            imageVector = boton.second,
                            contentDescription = boton.first,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    Text(text = boton.first, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun TopBarNavegacionPreview() {
    TopBarPrincipal()
}
