package com.example.myapplication.ui.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R


@Composable
fun LogoApp(modifier: Modifier = Modifier){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(top = 24.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = stringResource(R.string.logo_trazzo),
            modifier = Modifier
                .height(70.dp)
                .fillMaxSize()
                .padding(10.dp)
        )
    }
}

@Composable
fun Bienvenida(
    Texto1: Int,
    Texto2: Int,
    modifier: Modifier = Modifier){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally ,
        modifier = modifier
    ) {
        Text(
            stringResource(Texto1),
            modifier = Modifier.padding(3.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            color = colorResource(R.color.GrisOscuro)
        )
        Text(
            stringResource(Texto2),
            modifier = Modifier.padding(3.dp),
            fontSize = 14.sp,
            color = colorResource(R.color.GrisMedio)
        )
    }
}
@Composable
fun Iconos(
    IdImage : Int,
    Description : String,
    modifier: Modifier = Modifier
){
    Image(
        painter = painterResource(IdImage),
        contentDescription = Description,
        modifier = modifier.height(25.dp)
            .padding(start = 15.dp)
    )
}
@Composable
fun BotonInteres(
    Texto : String,
    Color: Int,
    ColorL: Int,
    modifier: Modifier = Modifier
){
    Button(
        onClick = { },
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = colorResource(Color)
        ),
        modifier = modifier.padding(vertical = 3.dp),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 2.dp)
    ) {
        Text(
            text = Texto,
            color = colorResource(ColorL),
            fontSize = 14.sp

        )
    }
}
@Composable
fun BotonIcono(
    Icon : Int,
    Description: String,
    Texto : String = "",
    Color: Int,
    ColorL: Int,
    modifier: Modifier = Modifier
){
    Button(
        onClick = { },
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = colorResource(Color)
        ),
        modifier = modifier.padding(vertical = 3.dp),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 2.dp)
    ) {
        Iconos(Icon, Description)
        Text(
            text = Texto,
            color = colorResource(ColorL),
            fontSize = 14.sp

        )
    }
}