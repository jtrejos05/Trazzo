package com.example.myapplication.navigation

import android.graphics.drawable.Icon
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SavedSearch
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.myapplication.R
import com.example.myapplication.data.Artista
import com.example.myapplication.data.local.ProveedorActividad
import com.example.myapplication.data.local.ProveedorBotonesDeNavegacion
import com.example.myapplication.data.local.ProveedorBotonesDeNavegacion.botones
import com.example.myapplication.data.local.ProveedorNotificaciones
import com.example.myapplication.data.local.ProveedorObras
import com.example.myapplication.ui.BuscarScreen
import com.example.myapplication.ui.CommentsScreen
import com.example.myapplication.ui.HomeScreen
import com.example.myapplication.ui.InicioSesionScreen
import com.example.myapplication.ui.PerfilScreen
import com.example.myapplication.ui.PrincipalScreen
import com.example.myapplication.ui.PublicacionesGuardadasScreen
import com.example.myapplication.ui.RegisterScreen
import com.example.myapplication.ui.SubirObraScreen
import com.example.myapplication.ui.VistaObrasScreen
import com.example.myapplication.ui.utils.LogoTrazzo

sealed class Rutas(
    val ruta: String
    ) {
    object Home : Rutas("home")
    object Login : Rutas("login")
    object Register : Rutas("register")
    object Subir : Rutas("subir")
    object Detalle : Rutas("detalle/{obraId}"){
        fun createRoute(obraId: Int): String {
            return "detalle/$obraId"
        }
    }
    object Perfil : Rutas("perfil")
    object Buscar : Rutas("buscar")
    object Guardadas : Rutas("guardadas")
    object Trending : Rutas("trending")
    object Barra: Rutas("barra")
    object Principal: Rutas("principal")
}
@Composable
fun AppNavigation(navControler: NavHostController,
    modifier: Modifier = Modifier){
    NavHost(navControler, Rutas.Home.ruta, modifier) {
        composable(Rutas.Home.ruta) {
            HomeScreen({ navControler.navigate(Rutas.Login.ruta) },
                { navControler.navigate(Rutas.Register.ruta) })
        }
        composable(Rutas.Principal.ruta) {
            PrincipalScreen(ProveedorObras.obras, "Usuario", )
        }
        composable(Rutas.Login.ruta) {
            InicioSesionScreen({ navControler.navigate(Rutas.Principal.ruta){
                popUpTo(0){inclusive=true}
            } }, { navControler.navigate(Rutas.Register.ruta) })
        }
        composable(Rutas.Register.ruta) {
            RegisterScreen({ navControler.navigate(Rutas.Login.ruta) })
        }
        composable(Rutas.Subir.ruta) {
            SubirObraScreen({ navControler.navigate(Rutas.Perfil.ruta) })
        }
        composable("detalle/{obraId}", arguments = listOf(navArgument("obraId"){type = NavType.IntType})) { backStackEntry ->
            val obraId = backStackEntry.arguments?.getInt("obraId") ?: 0
            val obra = ProveedorObras.obras.find { it.obraId == obraId }
            if (obra != null) {
                VistaObrasScreen(obra)
            }
        }
        composable(Rutas.Perfil.ruta) {
            val artista = Artista(
                img = R.drawable.maria_foto,
                correo = "Correo@gmail.com",
                contrasena = "123456",
                usuario = "Maria",
                edad = 20,
                profesion = "Artista",
                biografia = "Me encanta el arte y la pintura.",
                seguidores = 13,
                siguiendo = 1,
                likes = 20,
                obras = ProveedorObras.obras,
                interses = listOf("Pintura", "Escultura", "Fotografía")
            )
            PerfilScreen(artista, ProveedorActividad.actividades, ProveedorNotificaciones.notificaciones,{navControler.navigate(
                Rutas.Guardadas.ruta)},{ obraId->navControler.navigate(Rutas.Detalle.createRoute(obraId )) })
        }
        composable(Rutas.Buscar.ruta) {var texto by remember { mutableStateOf("") }
            BuscarScreen("",onTextoBusquedaChange = { texto = it })
        }
        composable(Rutas.Barra.ruta){
            TopNavigationBar({ navControler.navigate(Rutas.Buscar.ruta) })

        }
        composable(Rutas.Guardadas.ruta) {
            PublicacionesGuardadasScreen(ProveedorObras.obras,{ obraId->navControler.navigate(Rutas.Detalle.createRoute(obraId )) })
        }
        composable(Rutas.Trending.ruta) {
            Text("Pantalla de Tendencias")
        }


    }
}
@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route
    NavigationBar(modifier = modifier) {
        ProveedorBotonesDeNavegacion.botones.forEach { item ->
            val isSelected = currentRoute == item.route
            NavigationBarItem(
                icon ={
                    Icon(
                        imageVector = if(isSelected) item.icon else item.icon2,
                        contentDescription =  item.route
                    )
                } ,
                selected = false,
                onClick = { navController.navigate(item.route) }
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
fun TopNavigationBarPreview() {
    TopNavigationBar()
}
@Composable
fun TopNavigationBar(busquedaClicked: () -> Unit = {},modifier: Modifier = Modifier) {
    var busqueda by remember { mutableStateOf("") }
    Column(modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

            // Logo
            LogoTrazzo(
                modifier = Modifier
                    .size(32.dp)
            )




        // Barra de búsqueda

            OutlinedTextField(
                value = busqueda,
                onValueChange = { busqueda = it },
                placeholder = {
                    Text(stringResource(R.string.buscar_inspiraci_n_art_stica))
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clickable{
                        busquedaClicked()
                    }
            )


        Spacer(modifier = Modifier.height(8.dp))
    }

}

@Composable
fun NavigationBar(  navController: NavHostController,modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        TopNavigationBar()
        BottomNavigationBar(navController )
    }
}

