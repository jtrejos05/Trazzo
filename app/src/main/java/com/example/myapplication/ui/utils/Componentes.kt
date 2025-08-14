package com.example.myapplication.ui.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import java.text.Normalizer




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
        modifier = modifier.height(15.dp)
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
@Composable
fun Form(Icon : Int = 0,
         Description: String = "",
         Texto : String,
         Name: String,
         modifier: Modifier = Modifier,
         OP: Int = 0
){
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
        ) {
            if (Icon != 0) {
                if (OP == 1) {
                    Iconos(Icon, Description,modifier.padding(end = 2.dp))
                } else {
                    Iconos(Icon, Description)

                }
            }
            if (Icon == 0 || OP == 1){
                if (OP == 1){
                    Text(Texto,
                        modifier = Modifier.padding(vertical = 6.dp, horizontal = 4.dp),
                        color = colorResource(R.color.GrisOscuro),
                        fontSize = 20.sp
                    )
                }
                else{
                    Text(Texto,
                        modifier = Modifier.padding(vertical = 6.dp, horizontal = 18.dp),
                        color = colorResource(R.color.GrisOscuro),
                        fontSize = 20.sp
                    )
                }
            }else {
                Text(
                    Texto,
                    modifier = Modifier.padding(3.dp),
                    color = colorResource(R.color.GrisOscuro)
                )
            }
        }
        TextField(
            value = "",
            onValueChange = {},
            label = {Text(Name)},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 6.dp),
            shape = RoundedCornerShape(60.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorContainerColor = colorResource(R.color.RojoCoral),
                unfocusedLabelColor = colorResource(R.color.GrisMedio)
            )
        )
    }


}
@Composable
fun AddButton(
    texto: String,
    modifier: Modifier = Modifier
){
    Button(
        onClick = { /*TODO*/ },
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.verde_principal),
        ),
        modifier = modifier
    ) {
        Text(text = texto)
    }
}