package com.example.myapplication.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.ui.utils.Publicacion

@Composable
fun PublicacionesGuardadasScreen(
    publicaciones: List<Publicacion>,
    modifier: Modifier = Modifier
) {
    if (publicaciones.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No tienes publicaciones guardadas aún",
                color = colorResource(id = R.color.gris_texto_secundario)
            )
        }
    } else {
        LazyColumn(modifier = modifier.padding(vertical = 8.dp)) {
            items(publicaciones) { publicacion ->
                TarjetaPublicacion(publicacion)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PublicacionesGuardadasPreview() {
    val demo = listOf(
        Publicacion(
            id = 1,
            username = "Kenji Yamamoto",
            hora = "2h",
            titulo = "Grulla de origami paso a paso",
            descripcion = "Tutorial completo para crear la clásica grulla japonesa...",
            categorias = listOf("Origami", "Papel", "Tutorial"),
            likes = "325",
            comentarios = "12",
            compartidos = "8",
            idPerfil = R.drawable.maria_foto,
            idImagen = null // pon aquí un drawable si tienes, ej: R.drawable.fondo_negro
        ),
        Publicacion(
            id = 2,
            username = "Juanito Perez",
            hora = "5h",
            titulo = "Texturas en negro",
            descripcion = "Pack de texturas HD para fondos minimal.",
            categorias = listOf("Texturas", "Diseño"),
            likes = "98",
            comentarios = "3",
            compartidos = "1",
            idPerfil = R.drawable.juanito_foto,
            idImagen = null
        )
    )
    PublicacionesGuardadasScreen(publicaciones = demo)
}
