package com.example.myapplication.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.data.Obra
import com.example.myapplication.data.local.ProveedorObras
import com.example.myapplication.data.local.ProveedorComentarios
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.primaryLight


@Composable
fun VistaObrasScreen(
    obra: Obra,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .fillMaxSize(),
        ){
        PostCard(
            obra = obra
        )
    }
}

@Composable
fun PostCard(
    obra: Obra
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(12.dp)
    ) {
        // Encabezado
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = obra.fotous),
                contentDescription = "Foto de perfil",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )

            Spacer(Modifier.width(8.dp))

            Column {
                Text(obra.usuario, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(obra.hora, fontSize = 12.sp)
            }

            Spacer(Modifier.weight(1f))

            Text("⋯", fontSize = 20.sp) // Menú
        }

        Spacer(Modifier.height(12.dp))

        // Título y descripción
        Text(
            obra.titulo,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Spacer(Modifier.height(4.dp))
        Text(
            obra.descripcion,
            fontSize = 14.sp
        )

        Spacer(Modifier.height(8.dp))

        // Etiquetas
        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                items(obra.Tags){
                    Text(
                        it,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.tertiaryContainer, RoundedCornerShape(50))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
        }

        Spacer(Modifier.height(12.dp))

        // Imagen principal
        Image(
            painter = painterResource(id = obra.foto),
            contentDescription = "Imagen principal",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        // Reacciones
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ReactionItem(iconRes = R.drawable.like, count = obra.likes)
            ReactionItem(iconRes = R.drawable.comentarioicon, count = obra.comentarios)
            ReactionItem(iconRes = R.drawable.top_left_arrow, count = obra.compartidos)
            Image(
                painter = painterResource(id = R.drawable.icono_save),
                contentDescription = "Guardar",
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        InterfazExternaComentarios(cantidad = ProveedorComentarios.comentarios.size)
    }
}

@Composable
@Preview(showBackground = true)
fun PostCardPreview() {
    VistaObrasScreen(ProveedorObras.obras[1])
}

@Composable
fun ReactionItem(iconRes: Int, count: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = "Reacción",
            modifier = Modifier.size(24.dp)
        )
        Spacer(Modifier.width(4.dp))
        Text(count, fontSize = 12.sp, color = Color.Gray)
    }
}
