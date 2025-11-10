package com.example.myapplication.ui.Principal

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.testTag
import com.example.myapplication.ui.TarjetaPublicacion
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull

@Composable
fun PrincipalScreen( obraPressed: (String) -> Unit = {},
                     perfilPressed: (String)-> Unit = {},
    viewmodel: PrincipalViewModel,
    modifier: Modifier = Modifier.testTag("PrincipalScreen")
) {
    val state by viewmodel.uiState.collectAsState()
    val listState = rememberLazyListState()
    when{
        state.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                CircularProgressIndicator()
            }
        }
        state.errorMessage != null ->{
            Box(modifier= Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                Text(text= state.errorMessage ?: "Error desconocido")
            }
        }
        else -> {
            Box(
                modifier = modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {

                    Spacer(modifier = Modifier.height(8.dp))

                    //Barra divisora
                    HorizontalDivider(
                        modifier = Modifier.padding(bottom = 10.dp),
                        thickness = 1.dp,
                        color = DividerDefaults.color
                    )

                    //Saludo
                    Text(
                        text = "¡Hola!👋",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp
                        ),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Subtítulo
                    Text(
                        text = "Descubre nuevas obras de arte que te van a inspirar",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Lista de publicaciones
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.padding(horizontal = 16.dp),
                        state = listState,
                    ) {
                        itemsIndexed(state.obras) { index, obra ->
                            TarjetaPublicacion(
                                obra,
                                { obraPressed(obra.obraId) },
                                { perfilPressed(obra.artistaId) }
                            )

                            LaunchedEffect(listState, state.obras.size) {
                                Log.d("CARGAOBRAS", "Iniciando observer de scroll (${state.obras.size} obras)")

                                // Espera a que Compose mida el LazyColumn
                                delay(100L)

                                snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
                                    .filterNotNull()
                                    .distinctUntilChanged()
                                    .filter { lastVisibleIndex ->
                                        lastVisibleIndex == state.obras.lastIndex && !state.isLoading
                                    }
                                    .collect { lastVisibleIndex ->
                                        Log.d(
                                            "CARGAOBRAS",
                                            "Scroll llegó al final (índice=$lastVisibleIndex, total=${state.obras.size})"
                                        )
                                        if (!state.final){
                                            viewmodel.cargarSiguientes()
                                            // 1
                                            listState.scrollToItem(0)
                                        }
                                    }
                            }

                        }
                    }
                }
            }
        }
    }

}





