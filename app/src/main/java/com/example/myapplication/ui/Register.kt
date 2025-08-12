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
import com.example.myapplication.ui.utils.Form
import com.example.myapplication.ui.utils.LogoTrazzo


@Composable
@Preview(showBackground = true)
fun LogoAppRPreview(){
    LogoTrazzo(modifier = Modifier.height(7.dp))
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
        Form(R.drawable.correo,stringResource(R.string.icono_correo), stringResource(R.string.correo), stringResource(R.string.tu_email_com))
        Form(R.drawable.contrasenia,stringResource(R.string.icono_contrase_a),stringResource(R.string.contra),stringResource(R.string.minimo_6_caracteres))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(
                modifier.width(230.dp)
            ) {
                Form(R.drawable.usuario,stringResource(R.string.icono_usuario),stringResource(R.string.usuario),stringResource(R.string.nombre_artista))
            }
            Column {
                Form(R.drawable.edad,stringResource(R.string.icono_edad),stringResource(R.string.edad),stringResource(R.string._18))
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
        stringResource(R.string.pintura),
        stringResource(R.string.dibujo), stringResource(R.string.fotograf_a),
        stringResource(R.string.dise_o_digital),
        stringResource(R.string.origami), stringResource(R.string.escultura),
        stringResource(R.string.street_art), stringResource(R.string.moda),
        stringResource(R.string.ilustraci_n),
        stringResource(R.string.concept_art), stringResource(R.string.arte_tradicional),
        stringResource(R.string.collage), stringResource(R.string.acuarela),
        stringResource(R.string.leo), stringResource(R.string.acr_lico)
    )
    FlowRow(modifier = modifier.padding(start = 5.dp)) {
        intereses.forEach { interes -> BotonInteres(interes, R.color.VerdeClaroF,R.color.VerdaClaroL,
            Modifier
                .height(36.dp)
                .padding(horizontal = 6.dp)) }
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
        Form(R.drawable.briefcase_transparent, stringResource(R.string.icono_profesion),stringResource(R.string.profe),stringResource(R.string.selecciona_tu_profesion),modifier.height(50.dp))

        Form(R.drawable.biografia, stringResource(R.string.icono_biografia),stringResource(R.string.biografia),stringResource(R.string.cuentanos_sobre_ti_tu_estilo_artistico_experiencia_y_lo_que_te_apasina_del_arte))
    }

}
@Composable
@Preview(showBackground = true)
fun PerfilArtisticoPreview(){
    PerfilArtistico()
}
@Composable
fun InteresAritistico(){
    Column {
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
fun InteresAritisticoPreview(){
    InteresAritistico()
}
@Composable
fun BotonCrear(modifier: Modifier = Modifier){
    BotonInteres(stringResource(R.string.crear),R.color.MentaBri,R.color.GrisClaro ,modifier
        .width(370.dp)
        .height(60.dp))
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
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ){
            Spacer(modifier = Modifier.height(30.dp))
            LogoTrazzo(modifier= Modifier.height(70.dp))
            Bienvenida(R.string.bienvenida,R.string.creatividad)
            Spacer(modifier = Modifier.height(15.dp))
        }
        Column {
            InfoBasica()
            Spacer(modifier = Modifier.height(8.dp))
            PerfilArtistico()
            InteresAritistico()
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
        modifier = modifier
            .fillMaxSize()
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
