package com.example.myapplication.ui



import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.computeHorizontalBounds
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.ui.utils.Bienvenida
import com.example.myapplication.ui.utils.BotonInteres
import com.example.myapplication.ui.utils.Iconos
import com.example.myapplication.ui.utils.LogoApp


@Composable
@Preview(showBackground = true)
fun LogoAppRPreview(){
    LogoApp()
}
@Composable
@Preview(showBackground = true)
fun BienvenidaPreview(){
    Bienvenida(R.string.bienvenida,R.string.creatividad)
}
@Composable
fun InfoBasica(modifier: Modifier = Modifier){
    Column(
        modifier = modifier
    ){
        Text(
            stringResource(R.string.informacion),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 15.dp),
            color = colorResource(R.color.GrisOscuro)
        )
        Spacer(modifier = Modifier.height(5.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
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
            label = {Text(stringResource(R.string.tu_email_com))},
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
            label = {Text(stringResource(R.string.minimo_6_caracteres))},
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
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Iconos(R.drawable.usuario, stringResource(R.string.icono_usuario))
                    Text(
                        stringResource(R.string.usuario),
                        modifier = Modifier.padding(3.dp),
                        color = colorResource(R.color.GrisOscuro)
                    )
                }
                TextField(
                    value = "",
                    onValueChange = {},
                    label = {Text(stringResource(R.string.nombre_artista))},
                    modifier = Modifier
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
            Column {
               Row(verticalAlignment = Alignment.CenterVertically) {
                   Iconos(R.drawable.edad, stringResource(R.string.icono_edad))
                   Text(
                       stringResource(R.string.edad),
                       modifier = Modifier.padding(end = 15.dp),
                       color = colorResource(R.color.GrisOscuro)
                   )
               }
                TextField(
                    value = "",
                    onValueChange = {},
                    label = {Text(stringResource(R.string._18))},
                    modifier = Modifier
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

}
@Composable
@Preview(showBackground = true)
fun InfoBasicaPreview(){
    InfoBasica()
}
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Intereses(modifier: Modifier = Modifier){
    val intereses = listOf(
        "Pintura", "Dibujo", "Fotografía", "Diseño Digital",
        "Origami", "Escultura", "Street Art", "Moda",
        "Ilustración", "Concept Art", "Arte Tradicional",
        "Collage", "Acuarela", "Óleo", "Acrílico"
    )
    FlowRow(modifier = modifier.padding(start = 5.dp)) {
        intereses.forEach { interes -> BotonInteres(interes, R.color.VerdeClaroF,R.color.VerdaClaroL,
            Modifier.height(36.dp).padding(horizontal = 6.dp)) }
    }
}
@Composable
fun PerfilArtistico(modifier: Modifier = Modifier){
    Column(
        modifier = modifier
    ) {
        Text(
            stringResource(R.string.perfil),
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 3.dp),
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.GrisOscuro)
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            stringResource(R.string.profe),
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 3.dp),
            color = colorResource(R.color.GrisOscuro)
        )
        TextField(
            value = "",
            onValueChange = {},
            label = {Text(stringResource(R.string.selecciona_tu_profesion))},
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
            Iconos(R.drawable.biografia, stringResource(R.string.icono_biografia))
            Text(
                stringResource(R.string.biografia),
                modifier = Modifier.padding(3.dp),
                color = colorResource(R.color.GrisOscuro)
            )
        }
        TextField(
            value = "",
            onValueChange = {},
            label = {Text(stringResource(R.string.cuentanos_sobre_ti_tu_estilo_artistico_experiencia_y_lo_que_te_apasina_del_arte))},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 6.dp),
            shape = RoundedCornerShape(24.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorContainerColor = colorResource(R.color.RojoCoral),
                unfocusedLabelColor = colorResource(R.color.GrisMedio)
            )
        )
        Text(
            stringResource(R.string.intereses),
            modifier = Modifier.padding(start = 15.dp, top = 3.dp, bottom = 3.dp),
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.GrisOscuro)
        )
        Intereses()
        Text(
            stringResource(R.string.seleccionar),
            modifier = Modifier.padding(start = 15.dp, top = 3.dp, bottom = 3.dp),
            color = colorResource(R.color.GrisMedio)
        )
    }

}
@Composable
@Preview(showBackground = true)
fun PerfilArtisticoPreview(){
    PerfilArtistico()
}
@Composable
fun BotonCrear(modifier: Modifier = Modifier){
    BotonInteres(stringResource(R.string.crear),R.color.MentaBri,R.color.GrisClaro ,modifier.width(370.dp).height(60.dp))
}
@Composable
@Preview
fun BotonCrearPreview(){
    BotonCrear()
}
@Composable
fun BodyCrearCuenta(modifier: Modifier = Modifier){
    Column(
        modifier = modifier
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(modifier = Modifier.height(30.dp))
            LogoApp()
            Bienvenida(R.string.bienvenida,R.string.creatividad)
            Spacer(modifier = Modifier.height(15.dp))
        }
        Column {
            InfoBasica()
            Spacer(modifier = Modifier.height(8.dp))
            PerfilArtistico()
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            BotonCrear()
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                stringResource(R.string.anuncio),
                modifier = Modifier.padding(horizontal = 25.dp, vertical = 6.dp),
                color = colorResource(R.color.GrisMedio),
                fontSize = 15.sp
            )
        }
    }

}


@Composable
@Preview(showBackground = true)
fun BodyCrearCuentaPreview(){
    BodyCrearCuenta()
}
@Composable
fun Register(modifier: Modifier = Modifier){
    Column(
        modifier = modifier.fillMaxSize()
            .verticalScroll(rememberScrollState())
    ){
        BodyCrearCuenta()
        Spacer(modifier = Modifier.height(30.dp))
    }
}
@Composable
@Preview(showBackground = true)
fun RegisterPreview(){
    Register()
}
