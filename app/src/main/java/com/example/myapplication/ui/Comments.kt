package com.example.myapplication.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.ui.utils.UserProfileImage
import com.example.myapplication.ui.utils.Username


@Composable
fun Comment(
    modifier: Modifier = Modifier,
    hora: String,
    comentario: String,
    username: String,
    likes: String,
    idPerfil: Int
){
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top,
        modifier = modifier.padding(bottom = 20.dp)
    ){
        UserProfileImage(idImage = idPerfil, modifier = Modifier.padding(end = 10.dp))
        Column(){
            Row(modifier = Modifier.padding(bottom = 5.dp)){
                Username(username = username, modifier = Modifier.padding(end = 5.dp))
                Text(
                    text = hora,
                    color = colorResource(id = R.color.gris_texto_secundario)
                )
            }
            Text(
                comentario,
                color = colorResource(id = R.color.gris_texto_principal),
                modifier = Modifier.padding(bottom = 10.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ){
                Image(
                    painter = painterResource(id = R.drawable.favorite),
                    contentDescription = "Likes Image"
                )
                Text(
                    text = likes,
                    color = colorResource(id = R.color.gris_texto_secundario),
                    modifier = Modifier.padding(end = 40.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.top_left_arrow),
                    contentDescription = "Respond Image"
                )
                Text(
                    text = "Responder",
                    color = colorResource(id = R.color.gris_texto_secundario)
                )
            }
        }
    }
}

@Composable
fun Comments(
    modifier: Modifier = Modifier,
){
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
        modifier = modifier
    ) {
        Comment(
            username = "Maria Sato",
            comentario = "¡Increible tutorial! Por fin logre hacer mi primera grulla perfecta. Los pliegues estan explicados super claramente",
            likes = "12",
            idPerfil = R.drawable.maria_foto,
            hora = "2h"
        )
        Comment(
            username = "Juanito Perez",
            comentario = "Los pliegues estan explicados con mucha claridad",
            likes = "5",
            idPerfil = R.drawable.juanito_foto,
            hora = "5h"
        )
    }
}

@Composable
@Preview(showBackground = true)
fun CommentsPreview(){
    Comments()
}