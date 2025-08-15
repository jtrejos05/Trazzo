package com.example.myapplication.ui
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import com.example.myapplication.R
import com.example.myapplication.data.Obra
import com.example.myapplication.data.local.ProveedorObras

@Composable
fun PublicacionesGuardadasScreen(
    obras: List<Obra>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.GrisClaro))
            .padding(16.dp)
    ) {
        // Título
        Text(
            text = stringResource(id = R.string.publicaciones_guardadas),
            style = androidx.compose.material3.MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp,
                letterSpacing = 0.5.sp
            ),
            color = Color(0xFF19A05E),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            textAlign = TextAlign.Center
        )


        Spacer(modifier = Modifier.height(8.dp))

        // Categorías (tags)
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(listOf(
                R.string.categoria_inicio,
                R.string.categoria_trending,
                R.string.categoria_origami
            )) { cat ->
                Surface(
                    shape = RoundedCornerShape(50),
                    color = Color(0xFF19A05E) // Verde
                ) {
                    Text(
                        text = stringResource(id = cat),
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Lista de publicaciones
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(obras) { Obra ->
                TarjetaPublicacion(Obra)
            }
        }
    }
}

//Ejemplo publicaciones guardadas
@Preview(showBackground = true)
@Composable
fun PreviewPublicacionesGuardadas() {
    PublicacionesGuardadasScreen(ProveedorObras.obras)
}