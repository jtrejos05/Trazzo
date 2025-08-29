package com.example.myapplication.ui


import android.R.attr.description
import android.content.res.Resources
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Comment
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.request.Tags
import com.example.myapplication.R
import com.example.myapplication.data.ActividadItem
import com.example.myapplication.data.Artista
import com.example.myapplication.data.NotificacionItem
import com.example.myapplication.data.Obra
import com.example.myapplication.data.local.ProveedorActividad
import com.example.myapplication.data.local.ProveedorNotificaciones
import com.example.myapplication.data.local.ProveedorObras
import com.example.myapplication.ui.utils.BotonIcono
import com.example.myapplication.ui.utils.BotonInteres
import kotlin.Int
import kotlin.String


//TopBar unico de pantalla de Perfil
@Composable
fun TopBarProfile(
    username: String,
    onBackClick: () -> Unit,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Botón volver
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Volver",
                tint = MaterialTheme.colorScheme.primary
            )
        }


        // Nombre centrado
        Text(
            text = username,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )

        // Botón editar
        IconButton(onClick = onEditClick) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Editar Perfil",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}
@Composable
@Preview(showBackground = true)
fun PreviewTopBarProfile() {
    TopBarProfile(
        username = "Jjjjj",
        onBackClick = { /* TODO */ },
        onEditClick = { /* TODO */ }
    )
}
//Para mostrar los numeros del Perfil
@Composable
fun PerfilStat(valor: Int, label: String,
               modifier: Modifier = Modifier){
    Row(
    modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(valor.toString(), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.width(5.dp))
        Text(label, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(modifier = Modifier.width(10.dp))
    }

}
//Para mostrar los tags de profesion, y intereses artisticos
@Composable
fun Tags(artista: Artista,guardadoPressed: () -> Unit = {},
         modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(start = 15.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start) {
            Text(artista.profesion,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .padding(start = 10.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(horizontal = 6.dp, vertical = 4.dp),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.width(8.dp))
            guardados(guardadoPressed)
        }
        Row {
            artista.interses.forEach {  interes ->
                Card(
                    modifier = Modifier.padding(8.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = interes,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                    )
                }

            }
        }

    }
}
//Para mostrar Tosa la cabecera del Perfil
@Composable
fun ProfileHeader(
    artista: Artista,
    guardadoPressed: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = artista.img),
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {

                Row {
                    PerfilStat(artista.obras.size, "Posts")
                    PerfilStat(artista.seguidores, "Seguidores")
                    PerfilStat(artista.siguiendo, "Seguidos")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = artista.usuario,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = artista.profesion,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = artista.biografia,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.fillMaxWidth()
                )
            }

        }
        Tags(artista, guardadoPressed )
    }
}
@Composable
@Preview(showBackground = true)
fun PreviewProfileHeader() {
    ProfileHeader(
        artista = Artista(
            img= R.drawable.maria_foto,
            correo= "Correo@gmail.com",
            contrasena= "123456",
            usuario= "Maria",
            edad= 20,
            profesion= "Artista",
            biografia= "Me encanta el arte y la pintura.",
            seguidores= 13,
            siguiendo=1,
            likes= 20,
            obras= ProveedorObras.obras,
            interses = listOf("Pintura", "Escultura", "Fotografía")
        )
    )
}
//Para crear parte de el boton que lleva a donde se guardan los post
@Composable
fun guardados(guardadoPressed: () -> Unit = {},modifier: Modifier= Modifier) {
    Row(modifier = modifier.clickable(onClick = guardadoPressed),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        ) {
        Icon(
            imageVector = Icons.Default.Favorite,
            contentDescription = "Guardados",
            modifier = Modifier
                .padding(8.dp)
                .size(20.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(
            text = "Publicaciones Guardadas",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(end = 8.dp)
        )
    }

}
//Para la creacion de las tabs dentro de perfil
@Composable
fun TabsSection(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    val tabs = listOf(
        "Obras" to Icons.Default.Image,
        "Actividad" to Icons.Default.History,
        "Notificaciones" to Icons.Default.Notifications,
        "Stats" to Icons.Default.BarChart
    )

    TabRow(selectedTabIndex = selectedTab) {
        tabs.forEachIndexed { index, (title, icon) ->
            Tab(
                selected = selectedTab == index,
                onClick = { onTabSelected(index) },
                text = { Text(title) },
                icon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = if (selectedTab == index)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.onSurface
                    )
                }
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewTabsSection() {
    var selectedTab by remember { mutableStateOf(0) }
    TabsSection(
        selectedTab = selectedTab,
        onTabSelected = { selectedTab = it }
    )
}

//Tab que muestra las obras del usuario
@Composable
fun ObrasList(obras: List<Obra>,
              ObraPressed: (Int) -> Unit = {},
              modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(obras) { obra ->
            PostItem(obra, ObraPressed)
        }
    }
}
//Para crear los Elementos que se ven en las obras como likes y compartidos
@Composable
fun Elementos(painter: ImageVector, descripcion: String, texto: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = painter,
            contentDescription = descripcion,
            modifier = Modifier.padding(8.dp)
                .height(20.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = texto,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(end = 8.dp)
        )
    }



}
//Para la presentacion de las obras
@Composable
fun PostItem(post: Obra, ObraPressed: (Int) -> Unit, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp),
        onClick = { ObraPressed(post.obraId) }
    ) {
        Column {
            Image(
                painter = painterResource(id = post.foto),
                contentDescription = post.titulo,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = post.titulo,
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.bodyMedium
            )
            Row {
                Elementos(Icons.Default.Image, "Likes", post.likes)
                Elementos(Icons.Default.History, "Vistas", post.vistas)
                Elementos(
                    Icons.AutoMirrored.Filled.Comment
                    , "Comentarios", post.comentarios)

            }
        }
    }
}
@Composable
@Preview(showBackground = true)
fun PreviewObrasList() {
    ObrasList(ProveedorObras.obras)

}
//Tab con la actividad reciente del usuario
@Composable
fun ActividadTabContent(actividades: List<ActividadItem>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Text(
                "Actividad Reciente",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp)
            )
        }
        items(actividades) { actividad ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = actividad.icon,
                    contentDescription = null,
                    tint = actividad.color,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(Modifier.width(12.dp))
                Column {
                    Text(actividad.descripcion, style = MaterialTheme.typography.bodyMedium)
                    Text(
                        actividad.tiempo,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
//Tab con las notificaciones que le llegan al usuario

@Composable
fun NotificacionesTabContent(notificaciones: List<NotificacionItem>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Row(
                Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Notificaciones", style = MaterialTheme.typography.titleMedium)
                Text(
                    "Marcar todas como leídas",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        items(notificaciones) { notif ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.05f))
            ) {
                Row(
                    Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(notif.icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(notif.mensaje, style = MaterialTheme.typography.bodyMedium)
                        Text(
                            notif.tiempo,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}
//Tab que muestra estadisticas del usuario
@Composable
fun StatsTabContent(usuario: Artista) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Text("Estadísticas de Perfil", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(16.dp))
        }

        // Bloques de métricas
        item {
            Row(Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                MetricCard("Total Likes", "3,019", "+12% este mes", Icons.Default.Favorite, Color.Green, modifier = Modifier.weight(1f))
                MetricCard("Visualizaciones", "9,724", "+8% este mes", Icons.Default.Visibility, Color.Blue,  modifier = Modifier.weight(1f))
            }
        }

        // Crecimiento
        item {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("Crecimiento de Seguidores", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))
                    Text("Esta semana: +23")
                    Text("Este mes: +156")
                    Text("Este año: +2199")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
        // 🔹 Obras más populares en tarjeta
        item {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("Obras más populares", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))

                    PopularItem(1, "Composición Geométrica", "567","3200")
                    PopularItem(2, "Paisaje Abstracto", "456", "2300")
                    PopularItem(3, "Estudio de Color", "321", "1456")
                }
            }
        }
    }
}
//Para la muestra de las obras mas populares
@Composable
fun PopularItem(rango: Int, titulo: String, likes: String, views: String) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "$rango.",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.width(24.dp)
        )
        Column(Modifier.weight(1f)) {
            Text(titulo, style = MaterialTheme.typography.bodyMedium)
            Text("$likes likes • $views views", style = MaterialTheme.typography.bodySmall)
        }
    }
}



//para las cards que se muestra en el ultimo Tab
@Composable
fun MetricCard(title: String, value: String, subtitle: String, icon: ImageVector, color: Color, modifier: Modifier= Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f))
    ) {
        Column(Modifier.padding(16.dp)) {
            Icon(icon, contentDescription = null, tint = color)
            Text(value, style = MaterialTheme.typography.headlineSmall)
            Text(subtitle, style = MaterialTheme.typography.bodySmall)
        }
    }
}
//Pantalla final Perfil
@Composable
fun PerfilScreen(viewmodel: PerfilViewModel,usuario: Artista,
                  actividades: List<ActividadItem>,
                  notificaciones: List<NotificacionItem>,
                  modifier: Modifier = Modifier) {

    val state by viewmodel.uiState.collectAsState()
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        // Barra superior
        TopBarProfile(
            username = usuario.usuario,
            onBackClick = { /* TODO navegación atrás */ },
            onEditClick = {viewmodel.EditarPressed()}
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Cabecera
        ProfileHeader( usuario, {viewmodel.guardadoPressed()})

        // Tabs con íconos
        TabsSection(
            selectedTab = state.selectedTab,
            onTabSelected = { viewmodel.updateSelectedTab(it) }
        )

        // Contenido según el tab seleccionado
        when (state.selectedTab) {
            0 -> ObrasList(usuario.obras, {viewmodel.ObraPressed(it)}) // Tab "Obras"
            1 -> ActividadTabContent(actividades) // Tab "Actividad"
            2 -> NotificacionesTabContent(notificaciones) // Tab "Notificaciones"
            3 -> StatsTabContent(usuario) // Tab "Stats"
        }
    }
}





