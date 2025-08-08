package com.example.myapplication.ui.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myapplication.R

@Composable
fun UserProfileImage(
    idImage: Int,
    modifier: Modifier = Modifier
){
    Image(
        painter = painterResource(id = idImage),
        contentDescription = "User Profile Image",
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape)
    )
}

@Composable
fun Username(
    username: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = username,
        modifier = modifier,
        fontWeight = FontWeight.Bold,
        color = colorResource(id = R.color.gris_texto_principal)
    )
}