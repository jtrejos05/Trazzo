package com.example.myapplication.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import androidx.compose.ui.res.stringResource
import com.example.myapplication.data.Obra

@Composable
fun TarjetaPublicacion(
    publicacion: Obra,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.white)
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Perfil y nombre
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = publicacion.fotous),
                    contentDescription = stringResource(id = R.string.icono_usuario),
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(Modifier.width(8.dp))
                Column {
                    Text(
                        text = publicacion.usuario,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = publicacion.hora,
                        color = colorResource(id = R.color.gris_texto_secundario),
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(Modifier.height(10.dp))

            // Título
            Text(
                text = publicacion.titulo,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(Modifier.height(4.dp))

            // Descripción
            Text(
                text = publicacion.descripcion,
                style = MaterialTheme.typography.bodyMedium
            )

            // Categorías
            if (publicacion.Tags.isNotEmpty()) {
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    publicacion.Tags.forEach { cat ->
                        Surface(
                            shape = RoundedCornerShape(50),
                            color = Color(0xFFEFF8F1)
                        ) {
                            Text(
                                text = cat,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                color = Color(0xFF19A05E),
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }

            // Imagen principal
            publicacion.foto?.let { img ->
                Spacer(Modifier.height(12.dp))
                Image(
                    painter = painterResource(id = img),
                    contentDescription = stringResource(id = R.string.arte_tradicional),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(Modifier.height(12.dp))

            // Métricas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconoConTexto(R.drawable.like, publicacion.likes)
                IconoConTexto(R.drawable.comentarioicon, publicacion.comentarios)
                Image(
                    painter = painterResource(id = R.drawable.enviar),
                    contentDescription = stringResource(id = R.string.usuario),
                    modifier = Modifier.size(18.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.icono_save),
                    contentDescription = stringResource(id = R.string.icono_usuario),
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
fun IconoConTexto(iconId: Int, texto: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = iconId),
            contentDescription = null,
            modifier = Modifier.size(25.dp)
        )
        Spacer(Modifier.width(6.dp))
        Text(texto, color = colorResource(id = R.color.gris_texto_secundario))
    }
}