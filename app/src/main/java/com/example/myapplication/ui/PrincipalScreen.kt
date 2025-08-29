package com.example.myapplication.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import com.example.myapplication.R
import com.example.myapplication.data.Obra
import com.example.myapplication.data.local.ProveedorObras

@Composable
fun PrincipalScreen(
    obras: List<Obra>,
    nombreUsuario: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            Spacer(modifier = Modifier.height(8.dp))

            //Barra divisora
            Divider(
                color = Color.LightGray,
                thickness = 1.dp,
                modifier = Modifier.padding(bottom = 10.dp)
            )

            //Saludo
            Text(
                text = "¡Hola, $nombreUsuario!👋",
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
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                items(obras) { obra ->
                    TarjetaPublicacion(obra)
                }
            }
        }

        // Boton abajo a la derecha
        androidx.compose.material3.FloatingActionButton(
            onClick = { /* Acción cuando se presiona */ },
            containerColor = colorResource(id = R.color.verde_principal), // Verde
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            androidx.compose.material3.Icon(
                imageVector = androidx.compose.material.icons.Icons.Default.Add,
                contentDescription = "Agregar",
                tint = colorResource(id = R.color.white)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PrincipalScreenPreview() {
    PrincipalScreen(
        obras = ProveedorObras.obras,
        nombreUsuario = "tomi2"
    )
}


