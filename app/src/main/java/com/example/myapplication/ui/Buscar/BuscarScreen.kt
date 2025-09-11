package com.example.myapplication.ui.Buscar

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.R
import com.example.myapplication.ui.Buscar.BuscarViewModel
import com.example.myapplication.ui.TarjetaPublicacion
@Composable
fun BuscarScreen(
    viewmodel: BuscarViewModel = hiltViewModel(),
    onCategoriaClick: (String) -> Unit,
    onTrendingClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewmodel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Search bar
        TextField(
            value = state.texto,
            onValueChange = { nuevoTexto ->
                viewmodel.updateTexto(nuevoTexto)
            },
            placeholder = { Text(stringResource(R.string.buscar_inspiraci_n_art_stica)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Control
        when {
            state.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            state.errorMessage != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(state.errorMessage ?: "Error desconocido")
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { viewmodel.realizarBusqueda(state.texto) }) {
                            Text("Reintentar")
                        }
                    }
                }
            }
            state.mensajeSinResultados != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = state.mensajeSinResultados ?: "Mensaje no disponible",
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
            state.resultadosBusqueda.isNotEmpty() -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.resultadosBusqueda) { obra ->
                        TarjetaPublicacion(obra)
                    }
                }
            }
            else -> {
                // Contenido si no hay errores.
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Categorías estilo botón verde.
                    item {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(state.categoriasTop) { cat ->
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
                    // Trending
                    item {
                        Text(
                            text = stringResource(R.string.categoria_trending),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        )
                    }
                    item {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            for (i in state.trending.chunked(2)) {
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

                    // Búsquedas recientes.
                    item {
                        Text(
                            text = stringResource(R.string.publicaciones_guardadas),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        )
                    }
                    items(state.recientes) { rec ->
                        Text(
                            text = "🕒 $rec",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    // Explorar por categoria.
                    item {
                        Text(
                            text = stringResource(R.string.seleccionar),
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
                            items(state.explora) { cat ->
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
        }
    }
}


