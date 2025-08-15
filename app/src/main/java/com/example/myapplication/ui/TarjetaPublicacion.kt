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
import com.example.myapplication.ui.utils.Publicacion

@Composable
fun TarjetaPublicacion(publicacion: Publicacion) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Fila con perfil y nombre
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = publicacion.idPerfil),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(Modifier.width(8.dp))
                Column {
                    Text(
                        text = publicacion.username,
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

            // Chips categorías
            if (publicacion.categorias.isNotEmpty()) {
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    publicacion.categorias.forEach { cat ->
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
            publicacion.idImagen?.let { img ->
                Spacer(Modifier.height(12.dp))
                Image(
                    painter = painterResource(id = img),
                    contentDescription = "Imagen publicación",
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
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.like), // tu icono de like
                        contentDescription = "Likes",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(publicacion.likes, color = colorResource(id = R.color.gris_texto_secundario))
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.comentarioicon), // tu icono de comentarios
                        contentDescription = "Comentarios",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(publicacion.comentarios, color = colorResource(id = R.color.gris_texto_secundario))
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.enviar), // tu icono de compartir
                        contentDescription = "Compartir",
                        modifier = Modifier.size(18.dp)
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.icono_save), // tu icono de guardar
                        contentDescription = "Guardar",
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}
