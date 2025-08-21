package com.example.myapplication.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BuscarScreen() {
    //Contador para recordar búsquedas
    var textoBusqueda by remember { mutableStateOf("") }

    val categoriasTop = listOf("Todo", "Arte Digital", "Fotografía", "Pintura")
    val trending = listOf(
        "Arte Abstracto", "Retratos Digitales",
        "Paisajes", "Arte Conceptual",
        "Fotografía Urbana", "Ilustración Infantil",
        "Arte Minimalista", "Street Art"
    )
    val recientes = listOf("Arte Digital", "Retratos", "Paisajes Naturales")
    val explora = listOf("Arte Digital", "Fotografía", "Pintura", "Dibujo")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Barra de búsqueda
        item {
            TextField(
                value = textoBusqueda,
                onValueChange = { textoBusqueda = it },
                placeholder = { Text("Buscar inspiración artística...") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        }

        // Categorías superiores (chips)
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(categoriasTop) { cat ->
                    Surface(
                        shape = MaterialTheme.shapes.large,
                        color = Color(0xFF19A05E)
                    ) {
                        Text(
                            text = cat,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }

        // Trending en Arte
        item {
            Text(
                text = "Trending en Arte",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            )
        }
        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (i in trending.chunked(2)) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        i.forEach { t ->
                            Text(
                                text = t,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }

        // Búsquedas recientes
        item {
            Text(
                text = "Búsquedas Recientes",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            )
        }
        items(recientes) { rec ->
            Text(
                text = "🔎 $rec",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        // Explora por categoría
        item {
            Text(
                text = "Explora por Categoría",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            )
        }
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(explora) { cat ->
                    Surface(
                        shape = MaterialTheme.shapes.large,
                        color = Color(0xFFF0F0F0)
                    ) {
                        Text(
                            text = cat,
                            color = Color.Black,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewBuscarScreen() {
    BuscarScreen()
}