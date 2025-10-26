package com.example.myapplication.ui.InicioSesion

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.PersonAddAlt
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.ui.InicioSesion.InicioSesionViewModel
import com.example.myapplication.ui.utils.Bienvenida
import com.example.myapplication.ui.utils.BotonIcono
import com.example.myapplication.ui.utils.BotonInteres
import com.example.myapplication.ui.utils.Form
import com.example.myapplication.ui.utils.LogoTrazzo
import com.example.myapplication.ui.InicioSesion.AuthGoogle

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.navigation.Rutas
import com.example.myapplication.navigation.navigateSingleTopTo


@Composable
@Preview(showBackground = true)
fun LogoAppPreview() {
    LogoTrazzo(modifier = Modifier.height(70.dp))
}

@Composable
@Preview(showBackground = true)
fun BienvenidaIniPreview() {
    Bienvenida(R.string.iniciar_sesion, R.string.accede_a_tu_mundo_creativo)
}

@Composable
fun InfoInicio(
    correo: String,
    contraseña: String,
    onCorreoChange: (String) -> Unit,
    onContraseñaChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Form(
            Icons.Default.MailOutline,
            stringResource(R.string.icono_correo),
            stringResource(R.string.correo),
            stringResource(R.string.tu_email_com),
            correo,
            onCorreoChange
        )
        Form(
            Icons.Outlined.Lock,
            stringResource(R.string.icono_contrase_a),
            stringResource(R.string.contra),
            stringResource(R.string.minimo_6_caracteres),
            contraseña,
            onContraseñaChange,
            op = 2
        )
    }
}

@Composable
@Preview(showBackground = true)
fun InfoInicioPreview() {
    InfoInicio(
        correo = "",
        contraseña = "",
        onCorreoChange = {},
        onContraseñaChange = {}
    )
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
            Text(text = stringResource(R.string.recordarme))
        }

        Text(text = stringResource(R.string.olvidaste_tu_contrase_a))
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
fun Botones(
    loginButtonPressed: () -> Unit = {},
    registerButtonPressed: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        BotonInteres(
            stringResource(R.string.iniciar_sesion),
            MaterialTheme.colorScheme.primaryContainer,
            MaterialTheme.colorScheme.onSecondaryContainer,
            loginButtonPressed,
            modifier
                .width(370.dp)
                .height(60.dp)
        )
        Spacer(Modifier.height(10.dp))
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            HorizontalDivider(Modifier, DividerDefaults.Thickness, color = Color.LightGray)
            Box(modifier = Modifier.padding(horizontal = 8.dp)) {
                Text(
                    text = "o",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
        Spacer(Modifier.height(10.dp))
        BotonIcono(
            Icons.Default.PersonAddAlt,
            stringResource(R.string.icono_nueva_cuenta),
            stringResource(R.string.crear_cuenta_nueva),
            R.color.GrisClaro,
            R.color.GrisOscuro,
            registerButtonPressed,
            modifier
                .width(370.dp)
                .height(60.dp)
        )
    }
}

@Composable
fun InicioSesionScreen(
    viewmodel: InicioSesionViewModel,
    registerButtonPressed: () -> Unit = {},
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val state by viewmodel.uiState.collectAsState()

    Column(modifier = modifier) {
        // Logo y bienvenida
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(150.dp))
            LogoTrazzo(modifier = Modifier.height(70.dp))
            Bienvenida(R.string.iniciar_sesion, R.string.accede_a_tu_mundo_creativo)
            Spacer(modifier = Modifier.height(40.dp))
        }

        // Formulario
        Column {
            InfoInicio(
                state.correo,
                state.contraseña,
                onCorreoChange = { viewmodel.updateCorreo(it) },
                onContraseñaChange = { viewmodel.updateContraseña(it) }
            )
            PieDeInicio(
                recordarme = state.recordarme,
                onRecordarmeChanged = { viewmodel.updateRecordarme(it) }
            )
        }

        Spacer(Modifier.height(15.dp))

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (state.mostrarError) {
                Text(state.error)
            }

            Botones(
                { viewmodel.loginButtonPressed() },
                { registerButtonPressed() }
            )

            Spacer(Modifier.height(10.dp))

            val context = LocalContext.current
            val activity = context as Activity
            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartActivityForResult()
            ) { result ->
                AuthGoogle.handleResult(
                    result.data,
                    onSuccess = {
                        println("Inicio de sesión con Google exitoso")
                        navController.navigateSingleTopTo(Rutas.Principal.ruta)
                    },
                    onError = { error ->
                        println("Error Google Sign-In: $error")
                    }

                )
            }

            BotonGoogle(
                onClick = { AuthGoogle.signIn(activity, launcher) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Spacer(Modifier.height(140.dp))

            Text(
                stringResource(R.string.al_iniciar_sesion_aceptas_nuestros_terminos_de_servicio_y_politica_de_privacidad)
            )
        }
    }
}

@Composable
fun BotonGoogle(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .height(60.dp)
            .background(Color.White, RoundedCornerShape(12.dp))
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.google_logo),
            contentDescription = "Logo de Google",
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = "Continuar con Google",
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}