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
import com.example.myapplication.ui.utils.Form
import com.example.myapplication.ui.utils.LogoTrazzo


@Composable
@Preview(showBackground = true)
fun LogoAppPreview(){
    LogoTrazzo(modifier=Modifier.height(70.dp))
}
@Composable
@Preview(showBackground = true)
fun BienvenidaIniPreview(){
    Bienvenida(R.string.iniciar_sesion, R.string.accede_a_tu_mundo_creativo)
}
@Composable
fun InfoInicio(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Form(R.drawable.correo, stringResource(R.string.icono_correo),stringResource(R.string.correo),stringResource(R.string.tu_email_com))
        Form(R.drawable.contrasenia, stringResource(R.string.icono_contrase_a),stringResource(R.string.contra),stringResource(R.string.minimo_6_caracteres))

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
                text = stringResource(R.string.recordarme)
            )
        }

        Text(
            text = stringResource(R.string.olvidaste_tu_contrase_a),
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
fun InicioSesionScreen(modifier: Modifier = Modifier){
    var recordarme by remember { mutableStateOf(false) }
    Column(modifier = modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(150.dp))
            LogoTrazzo(modifier=Modifier.height(70.dp))
            Bienvenida(R.string.iniciar_sesion, R.string.accede_a_tu_mundo_creativo)
            Spacer(modifier = Modifier.height(40.dp))
        }
        Column {
            InfoInicio()
            PieDeInicio(recordarme = recordarme,
                onRecordarmeChanged = { recordarme = it })
        }
        Spacer(modifier.height(15.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Botones()
            Spacer(modifier.height(140.dp))
            Text(stringResource(R.string.al_iniciar_sesion_aceptas_nuestros_terminos_de_servicio_y_politica_de_privacidad))
        }
    }
}
@Composable
@Preview(showBackground = true)
fun InicioSesionPreview(){
    InicioSesionScreen()
}