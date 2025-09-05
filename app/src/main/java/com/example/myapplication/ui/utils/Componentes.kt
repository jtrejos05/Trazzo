package com.example.myapplication.ui.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DoNotDisturbOnTotalSilence
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R


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
            fontSize = 22.sp
        )
        Text(
            stringResource(Texto2),
            modifier = Modifier.padding(3.dp),
            fontSize = 14.sp
        )
    }
}
@Composable
fun Iconos(
    imagen : ImageVector,
    Description : String,
    modifier: Modifier = Modifier
){
   Icon(
         imageVector = imagen,
         contentDescription = Description,
         tint = MaterialTheme.colorScheme.primary,
         modifier = modifier.padding(start = 16.dp)
   )
}
@Composable
fun BotonInteres(
    Texto: String,
    Color: Color,
    ColorL: Color,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
){
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color
        ),
        modifier = modifier.padding(vertical = 3.dp),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 2.dp)
    ) {
        Text(
            text = Texto,
            color = ColorL,
            fontSize = 14.sp

        )
    }
}
@Composable
fun BotonIcono(
    Icon : ImageVector,
    Description: String,
    Texto : String = "",
    Color: Int,
    ColorL: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Button(
        onClick = onClick,
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
fun Form(icon : ImageVector = Icons.Default.DoNotDisturbOnTotalSilence,
         description: String = "",
         texto : String,
         name: String,
         dato: String,
         onChange: (String) -> Unit = {},
         modifier: Modifier = Modifier,
         op: Int = 0
){
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
        ) {
            if (icon != Icons.Default.DoNotDisturbOnTotalSilence) {
                if (op == 1) {
                    Iconos(icon, description,modifier.padding(end = 2.dp))
                } else {
                    Iconos(icon, description)

                }
            }
            if (icon == Icons.Default.DoNotDisturbOnTotalSilence || op == 1){
                if (op == 1){
                    Text(texto,
                        modifier = Modifier.padding(vertical = 6.dp, horizontal = 4.dp),
                        fontSize = 20.sp
                    )
                }
                else{
                    Text(texto,
                        modifier = Modifier.padding(vertical = 6.dp, horizontal = 18.dp),
                        fontSize = 20.sp
                    )
                }
            }else {
                Text(
                    texto,
                    modifier = Modifier.padding(3.dp)
                )
            }
        }
        if (op == 2){
            TextField(
                value = dato,
                onValueChange = onChange,
                label = {Text(name)},
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
                ),
                visualTransformation = PasswordVisualTransformation()
            )
        }else{
            TextField(
                value = dato,
                onValueChange = onChange,
                label = {Text(name)},
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


}
@Composable
fun AddButton(
    texto: String,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
){
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        modifier = modifier
    ) {
        Text(text = texto)
    }
}

@Composable
fun ReactionItem(imagen: ImageVector, descripcion: String, count: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = imagen,
            contentDescription = descripcion,
            tint = MaterialTheme.colorScheme.primary,
        )
        Spacer(Modifier.width(4.dp))
        Text(count, fontSize = 12.sp)
    }
}