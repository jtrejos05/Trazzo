package com.example.myapplication.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.data.local.ProveedorComentarios
import com.example.myapplication.ui.utils.Comment
import com.example.myapplication.ui.utils.UserProfileImage
import com.example.myapplication.ui.utils.Username

// Pantalla dedicada a los comentarios
@Composable
fun CommentsScreen(
    modifier: Modifier = Modifier,
){
    LazyColumn(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
        modifier = modifier
    ) {
        // Muestra en una columna los comentarios
        items(ProveedorComentarios.comentarios.size) {
            Comment(
                hora = ProveedorComentarios.comentarios[it].hora,
                comentario = ProveedorComentarios.comentarios[it].comentario,
                username = ProveedorComentarios.comentarios[it].usuario,
                likes = ProveedorComentarios.comentarios[it].likes.toString(),
                idPerfil = ProveedorComentarios.comentarios[it].fotous
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CommentsPreview(){
    CommentsScreen()
}