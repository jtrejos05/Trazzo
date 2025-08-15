package com.example.myapplication.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
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
import coil.compose.AsyncImage
import com.example.myapplication.R


@Composable
fun VistaObras(
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier
    ){
        Column(
            modifier = Modifier,
        ){
            PostCard()
        }
    }
}

@Composable
fun PostCard() {
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
                painter = painterResource(id = R.drawable.maria_foto),
                contentDescription = "Foto de perfil",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )

            Spacer(Modifier.width(8.dp))

            Column {
                Text("Kenji Yamamoto", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text("2h", fontSize = 12.sp)
            }

            Spacer(Modifier.weight(1f))

            Text("⋯", fontSize = 20.sp) // Menú
        }

        Spacer(Modifier.height(12.dp))

        // Título y descripción
        Text(
            "Grulla de origami paso a paso",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Spacer(Modifier.height(4.dp))
        Text(
            "Tutorial completo para crear la clásica grulla japonesa. Técnica tradicional con papel washi.\n" +
                    "Perfecto para principiantes que quieren dominar esta hermosa forma de arte.",
            fontSize = 14.sp
        )

        Spacer(Modifier.height(8.dp))

        // Etiquetas
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("#origami", "#grulla", "#papel", "#tutorial", "#japonés").forEach { tag ->
                Text(
                    tag,
                    color = Color(0xFF2E7D32),
                    fontSize = 12.sp,
                    modifier = Modifier
                        .background(Color(0xFFE8F5E9), RoundedCornerShape(50))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        // Imagen principal
        Image(
            painter = painterResource(id = R.drawable.grulla),
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
            ReactionItem(iconRes = R.drawable.like, count = 325)
            ReactionItem(iconRes = R.drawable.comentarioicon, count = 12)
            ReactionItem(iconRes = R.drawable.top_left_arrow, count = 3)
            /**Icon(
                imageVector = Icons.Default.BookmarkBorder,
                contentDescription = "Guardar",
                modifier = Modifier.size(20.dp)
            )**/
            Image(
                painter = painterResource(id = R.drawable.icono_save),
                contentDescription = "Guardar",
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PostCardPreview() {
    VistaObras()
}

@Composable
fun ReactionItem(iconRes: Int, count: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = "Reacción",
            modifier = Modifier.size(24.dp)
        )
        Spacer(Modifier.width(4.dp))
        Text(count.toString(), fontSize = 12.sp, color = Color.Gray)
    }
}
