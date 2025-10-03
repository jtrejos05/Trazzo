package com.example.myapplication.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Comment
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ThumbUpOffAlt
import androidx.compose.material.icons.filled.TurnRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.data.Obra
import com.example.myapplication.ui.utils.ReactionItem
import com.example.myapplication.ui.utils.obraAssyncImage
import com.example.myapplication.ui.utils.profileAssyncImage


@Composable
fun TarjetaPublicacion(
    publicacion: Obra,
    obraClicked: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        onClick = { obraClicked(publicacion.obraId) },
        modifier = modifier
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Perfil y nombre
            Row(verticalAlignment = Alignment.CenterVertically) {
                profileAssyncImage(publicacion.fotous,36)
                Spacer(Modifier.width(8.dp))
                Column {
                    Text(
                        text = publicacion.usuario,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = publicacion.hora,
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
                            shape = RoundedCornerShape(50)
                        ) {
                            Text(
                                text = cat,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }

            // Imagen principal
            publicacion.foto?.let { img ->
                Spacer(Modifier.height(12.dp))
                obraAssyncImage(img,200,Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)))

            }

            Spacer(Modifier.height(12.dp))

            // Métricas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ReactionItem(imagen = Icons.Default.ThumbUpOffAlt, descripcion = stringResource(R.string.like), count = publicacion.likes)
                ReactionItem(imagen = Icons.AutoMirrored.Filled.Comment, descripcion = stringResource(R.string.comentarios), count = publicacion.comentarios)
                ReactionItem(imagen = Icons.Default.TurnRight, descripcion = stringResource(R.string.compartidos), count = publicacion.compartidos)
                Icon(
                    imageVector = Icons.Default.BookmarkBorder,
                    contentDescription = stringResource(R.string.guardar),
                    tint = MaterialTheme.colorScheme.primary
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
        Text(texto)
    }
}