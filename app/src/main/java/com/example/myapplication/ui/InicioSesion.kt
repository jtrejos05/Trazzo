package com.example.myapplication.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.ui.utils.Bienvenida
import com.example.myapplication.ui.utils.BotonIcono
import com.example.myapplication.ui.utils.BotonInteres
import com.example.myapplication.ui.utils.Iconos
import com.example.myapplication.ui.utils.LogoApp


@Composable
@Preview(showBackground = true)
fun LogoAppPreview(){
    LogoApp()
}
@Composable
@Preview(showBackground = true)
fun BienvenidaIniPreview(){
    Bienvenida(R.string.iniciar_sesion, R.string.accede_a_tu_mundo_creativo)
}
@Composable
fun InfoInicio(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Row {
            Iconos(R.drawable.correo, stringResource(R.string.icono_correo))
            Text(
                stringResource(R.string.correo),
                modifier = Modifier.padding(3.dp),
                color = colorResource(R.color.GrisOscuro)
            )
        }
        TextField(
            value = "",
            onValueChange = {},
            label = { Text(stringResource(R.string.tu_email_com)) },
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
        Row(verticalAlignment = Alignment.CenterVertically) {
            Iconos(R.drawable.contrasenia, stringResource(R.string.icono_contrase_a))
            Text(
                stringResource(R.string.contra),
                modifier = Modifier.padding(3.dp),
                color = colorResource(R.color.GrisOscuro)
            )
        }
        TextField(
            value = "",
            onValueChange = {},
            label = { Text(stringResource(R.string.minimo_6_caracteres)) },
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
@Preview(showBackground = true)
fun InfoInicioPreview(){
    InfoInicio()
}
@Composable
fun PieDeInicio(
    recordarme: Boolean,
    onRecordarmeChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = recordarme,
                onCheckedChange = onRecordarmeChanged
            )
            Text(
                text = "Recordarme"
            )
        }

        Text(
            text = "¿Olvidaste tu contraseña?",
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PieDeInicioPreview() {
    var recordarme by remember { mutableStateOf(false) }

    MaterialTheme {
        PieDeInicio(
            recordarme = recordarme,
            onRecordarmeChanged = { recordarme = it }
        )
    }
}

@Composable
fun Botones(modifier: Modifier = Modifier){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        BotonInteres(stringResource(R.string.iniciar_sesion),R.color.MentaBri,R.color.GrisClaro ,modifier
            .width(370.dp)
            .height(60.dp))
        Spacer(Modifier.height(10.dp))
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Divider(color = Color.LightGray)
            Box(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = "o",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
        Spacer(modifier.height(10.dp))
        BotonIcono(R.drawable.nuevacuenta, stringResource(R.string.icono_nueva_cuenta),
            stringResource(R.string.crear_cuenta_nueva),R.color.GrisClaro,R.color.GrisOscuro,modifier
                .width(370.dp)
                .height(60.dp))
    }
}
@Composable
fun CuentaEjemplo(modifier: Modifier = Modifier){
    Column (
        modifier = modifier.padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text("Cuentas de demostracion")
        Spacer(modifier.height(8.dp))
        Column (
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(colorResource(R.color.GrisClaro))
                .height(70.dp)
                .padding(horizontal = 7.dp, vertical = 3.dp)
        ){
            Text("Sofia Art")
            Text("Artista digital profesional")
        }
        Spacer(modifier.height(8.dp))
        Column (
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(colorResource(R.color.GrisClaro))
                .height(70.dp)
                .padding(horizontal = 7.dp, vertical = 3.dp)
        ) {
            Text("Carlos Origami")
            Text("Maestro del papel")
        }
        Spacer(modifier.height(8.dp))
        Text(stringResource(R.string.al_iniciar_sesion_aceptas_nuestros_terminos_de_servicio_y_politica_de_privacidad))
    }
}

@Composable
fun InicioSesion(modifier: Modifier = Modifier){
    var recordarme by remember { mutableStateOf(false) }
    Column {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            LogoApp()
            Bienvenida(R.string.iniciar_sesion, R.string.accede_a_tu_mundo_creativo)
            Spacer(modifier = Modifier.height(15.dp))
        }
        Column {
            InfoInicio()
            PieDeInicio(recordarme = recordarme,
                onRecordarmeChanged = { recordarme = it })
        }
        Spacer(modifier.height(8.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Botones()
            Spacer(modifier.height(20.dp))
            CuentaEjemplo()
        }
    }
}
@Composable
@Preview(showBackground = true)
fun InicioSesionPreview(){
    InicioSesion()
}