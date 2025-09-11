package com.example.myapplication.ui.Principal

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.data.Obra
import com.example.myapplication.ui.Principal.PrincipalViewModel
import com.example.myapplication.ui.TarjetaPublicacion
import androidx.compose.ui.graphics.Color

@Composable
fun PrincipalScreen(
    obraPressed: (Int) -> Unit = {},
    viewmodel: PrincipalViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewmodel.uiState.collectAsState()

    if (state.isLoading) {
        // Muestra indicador de carga mientras se obtienen los datos.
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (state.errorMessage != null) {
        // Muestra un mensaje de error y un botón para reintentar en caso de tener problemas.
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = state.errorMessage ?: "Error desconocido",
                    //color = colorResource(R.color.rojo_error),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { viewmodel.getObras() }) {
                    Text("Reintentar")
                }
            }
        }
    } else {
        // Muestra la lista de publicaciones si no hay errores.
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(
                modifier = Modifier.padding(bottom = 10.dp),
                thickness = 1.dp,
                color = DividerDefaults.color
            )
            Text(
                text = "¡Hola!👋",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                ),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Descubre nuevas obras de arte que te van a inspirar",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                items(state.obras) { obra ->
                    TarjetaPublicacion(obra, { obraPressed(obra.obraId) })
                }
            }
        }
    }
}




