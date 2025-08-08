package com.example.myapplication.ui.utils

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.myapplication.R

@Composable
fun LogoTrazzo(
    modifier: Modifier = Modifier
){
    Image (
        painter = painterResource(id = R.drawable.trazzo),
        contentDescription = "Logo App",
        modifier = modifier
    )
}