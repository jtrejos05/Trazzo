package com.example.myapplication.ui.utils

import android.util.Log
import android.widget.RatingBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material.icons.filled.TurnLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.myapplication.R

@Composable
fun Calificacion(
    calificacion: Double,
    modifier: Modifier = Modifier
){
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        var cal = calificacion;
        for (i in 1..5) {
            if (cal >=0.5) {
                if (cal == 0.5) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.StarHalf,
                        contentDescription = "estrella media",
                        tint = MaterialTheme.colorScheme.inversePrimary,
                        modifier = modifier.size(20.dp)
                    )
                    cal = cal-1
                } else {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "estrella completa",
                        tint = MaterialTheme.colorScheme.inversePrimary,
                        modifier = modifier.size(20.dp)
                    )
                    cal = cal-1
                }
            } else {
                Icon(
                    imageVector = Icons.Default.StarBorder,
                    contentDescription = "estrella vacia",
                    tint = MaterialTheme.colorScheme.inversePrimary,
                    modifier = modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun Comment(
    modifier: Modifier = Modifier,
    hora: String,
    comentario: String,
    username: String,
    likes: String,
    idPerfil: String,
    calificacion: Double,
    respoderClicked: ()-> Unit,
    likeClicked: ()-> Unit,
    liked: Boolean,
    foto:String? = null,
    likesTag: String ="",
    likeButtonTag:String=""
){
    var like = liked
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top,
        modifier = modifier.padding(bottom = 20.dp)
    ){
        profileAssyncImage(profileImage = idPerfil, size = 40, modifier = Modifier.padding(end = 10.dp))
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
                Spacer(modifier= Modifier.width(30.dp))
                Calificacion(calificacion)
            }
            Text(
                comentario,
                modifier = Modifier.padding(bottom = 10.dp)

            )
            if(foto != null || foto.equals("")){
                obraAssyncImage(foto!!,150)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,

            ){
                Icon(
                    imageVector = if (like){Icons.Default.Favorite} else Icons.Default.FavoriteBorder,
                    contentDescription = "Like Icon",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable(
                        onClick = likeClicked
                    )
                        .testTag(likeButtonTag)
                )
                Text(
                    text = likes,
                    modifier = Modifier
                        .padding(end = 40.dp)
                        .testTag(likesTag)
                )
                Icon(
                    imageVector = Icons.Default.TurnLeft,
                    contentDescription = "Reply Icon",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable{
                        respoderClicked()
                    }
                )
                Text(
                    text = "Responder",
                )
            }
        }
    }
}