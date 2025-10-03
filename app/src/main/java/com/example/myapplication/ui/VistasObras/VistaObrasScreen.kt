package com.example.myapplication.ui.VistasObras


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.data.Obra
import com.example.myapplication.data.local.ProveedorComentarios
import com.example.myapplication.ui.utils.Comment
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Comment
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ThumbUpOffAlt
import androidx.compose.material.icons.filled.TurnRight
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import com.example.myapplication.R
import com.example.myapplication.ui.utils.ReactionItem
import com.example.myapplication.ui.utils.obraAssyncImage
import com.example.myapplication.ui.utils.profileAssyncImage


@Composable
fun VistaObrasScreen(
    idObra: String,
    viewmodel: VistaObrasViewModel,
    modifier: Modifier = Modifier
){
    val state by viewmodel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        val obra = viewmodel.getObra(idObra.toInt())
        viewmodel.updateObra(obra)
    }
    if (state.obra != null){
        Column(
            modifier = modifier
                .fillMaxSize(),
        ){
            PostCard(
                obra = state.obra!!
            )
        }
    }

}
@Composable
fun Encabezado(
    obra: Obra,
    modifier: Modifier = Modifier
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        profileAssyncImage(obra.fotous,40)

        Spacer(Modifier.width(8.dp))

        Column {
            Text(obra.usuario, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(obra.hora, fontSize = 12.sp)
        }

        Spacer(Modifier.weight(1f))
    }
    Spacer(Modifier.height(12.dp))
}

@Composable
fun TituloyDescripcion(
    obra: Obra,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
    ){
        Text(
            obra.titulo,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Spacer(Modifier.height(4.dp))
        Text(
            obra.descripcion,
            fontSize = 14.sp
        )
        Spacer(Modifier.height(8.dp))
    }
}

@Composable
fun Etiquetas(
    obra: Obra,
    modifier: Modifier = Modifier
){
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        modifier = modifier
    ) {
        items(obra.Tags){
            Text(
                it,
                fontSize = 12.sp,
                modifier = Modifier
                    .clip(RoundedCornerShape(50.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }

    Spacer(Modifier.height(12.dp))
}

@Composable
fun ImagenPrincipal(
    obra: Obra,
    modifier: Modifier = Modifier
){
    Column (
        modifier = modifier
    ) {
        obraAssyncImage(obra.foto, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(12.dp))
    }
}

@Composable
fun Reacciones(
    obra: Obra,
    modifier: Modifier = Modifier
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        ReactionItem(imagen = Icons.Default.ThumbUpOffAlt, descripcion = "like", count = obra.likes)
        ReactionItem(imagen = Icons.AutoMirrored.Filled.Comment, descripcion = "comentarios", count = obra.comentarios)
        ReactionItem(imagen = Icons.Default.TurnRight, descripcion = "compartidos", count = obra.compartidos)
        Icon(
            imageVector = Icons.Default.BookmarkBorder,
            contentDescription = "guardar",
            tint = MaterialTheme.colorScheme.primary
        )
    }
    Spacer(modifier = Modifier.height(30.dp))
}
@Composable
fun PostCard(
    obra: Obra
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        item {
            Encabezado(obra)
            TituloyDescripcion(obra)
            Etiquetas(obra)
            ImagenPrincipal(obra)
            Reacciones(obra)
            Text(
                text = stringResource(R.string.comentarios),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Spacer(Modifier.height(8.dp))
        }
        items(ProveedorComentarios.comentarios.size) {
            Comment(
                hora = ProveedorComentarios.comentarios[it].hora,
                comentario = ProveedorComentarios.comentarios[it].comentario,
                username = ProveedorComentarios.comentarios[it].usuario,
                likes = ProveedorComentarios.comentarios[it].likes.toString(),
                idPerfil = ProveedorComentarios.comentarios[it].fotous,
                calificacion = ProveedorComentarios.comentarios[it].calificacion
            )
        }
    }
}
