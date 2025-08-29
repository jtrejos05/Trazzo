package com.example.myapplication.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Comment
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.ui.utils.UserProfileImage


@Composable
fun Interfaz(
    cantidad: Int
) {
    Column() {
        // Encabezado de la seccion de comentarios
        Row() {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Comment,
                contentDescription = "Imagen comentario"
            )
            Text(
                text = "$cantidad comentarios",
            )
        }

        // Seccion de comentarios
        CommentsScreen()

        // Seccion de enviar comentario
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            UserProfileImage(
                idImage = R.drawable.maria_foto,
                modifier = Modifier.padding(end = 5.dp)
            )
            OutlinedTextField(
                value = "",
                onValueChange = {},
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .padding(end = 5.dp)
                    .width(250.dp),
            )
            Button(
                onClick = { /*TODO*/ },
                content = {
                    Image(
                        painter = painterResource(R.drawable.enviar),
                        contentDescription = "Enviar",
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier.size(20.dp)
                    )
                },
                colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
            ),
            )
        }
    }
}

@Composable
fun InterfazExternaComentarios(
    modifier: Modifier = Modifier,
    cantidad: Int
) {

    Card(
        modifier = modifier,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        colors = CardDefaults.cardColors()
    ) {
        Box(modifier = Modifier.padding(10.dp)){
            Interfaz(cantidad = cantidad)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun InterfazExternaComentariosPreview() {
    InterfazExternaComentarios(
        cantidad = 2
    )
}