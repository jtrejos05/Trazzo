package com.example.myapplication.ui.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.TurnLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.R

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
            Row(
                modifier = Modifier.padding(bottom = 5.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center

            ){
                Username(username = username, modifier = Modifier.padding(end = 5.dp))
                Text(
                    text = hora,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = MaterialTheme.typography.bodySmall.fontSize
                )
            }
            Text(
                comentario,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ){
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Like Icon",
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = likes,
                    modifier = Modifier.padding(end = 40.dp)
                )
                Icon(
                    imageVector = Icons.Default.TurnLeft,
                    contentDescription = "Reply Icon",
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Responder",
                )
            }
        }
    }
}