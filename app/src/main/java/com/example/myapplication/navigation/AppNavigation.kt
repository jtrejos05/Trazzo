package com.example.myapplication.navigation

import android.graphics.drawable.Icon
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Start
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
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
import androidx.lifecycle.viewmodel.compose.viewModel
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
import com.example.myapplication.ui.BuscarViewModel
import com.example.myapplication.ui.EditarPerfilScreen
import com.example.myapplication.ui.EditarPerfilViewModel
import com.example.myapplication.ui.HomeScreen
import com.example.myapplication.ui.HomeViewModel
import com.example.myapplication.ui.InicioSesionScreen
import com.example.myapplication.ui.InicioSesionViewModel
import com.example.myapplication.ui.PerfilScreen
import com.example.myapplication.ui.PerfilViewModel
import com.example.myapplication.ui.PrincipalScreen
import com.example.myapplication.ui.PrincipalViewModel
import com.example.myapplication.ui.PublicacionesGuardadasScreen
import com.example.myapplication.ui.PublicacionesGuardadasViewModel
import com.example.myapplication.ui.RegisterScreen
import com.example.myapplication.ui.RegisterViewModel
import com.example.myapplication.ui.SubirObraScreen
import com.example.myapplication.ui.SubirObraViewModel
import com.example.myapplication.ui.TrendingScreen
import com.example.myapplication.ui.TrendingViewModel
import com.example.myapplication.ui.VistaObrasScreen
import com.example.myapplication.ui.utils.LogoTrazzo

//Sealed Class con las rutas a las pantallas
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
    object EditarPerfil: Rutas("editarperfil")
}
//Todo lo relacionado a la navegacion entre pantallas
@Composable
fun AppNavigation(navControler: NavHostController,
    modifier: Modifier = Modifier){
    NavHost(navControler, Rutas.Home.ruta, modifier) {
        //Navegacion Home
        composable(Rutas.Home.ruta) {
            val viewModel: HomeViewModel= viewModel()
            val state by viewModel.uiState.collectAsState()
            if (state.navegar){
                navControler.navigateSingleTopTo(Rutas.Login.ruta)
                viewModel.resetFlag()
            }else if (state.navegarRegister){
                navControler.navigateSingleTopTo(Rutas.Register.ruta)
                viewModel.resetFlag()
            }
            HomeScreen(viewModel)
        }
        //Navegacion Pagina principal
        composable(Rutas.Principal.ruta) {
            val viewmodel: PrincipalViewModel = viewModel()
            val state by viewmodel.uiState.collectAsState()

            if (state.navegar){
                navControler.navigateSingleTopTo(Rutas.Detalle.createRoute(state.obra))
                viewmodel.resetFlag()
            }
            PrincipalScreen(ProveedorObras.obras, "Usuario", viewmodel)
        }
        //Navegacion Inicio de Sesion
        composable(Rutas.Login.ruta) {
            val viewmodel: InicioSesionViewModel= viewModel()
            val state by viewmodel.uiState.collectAsState()

            if (state.navegar){
                navControler.navigateSingleTopTo(Rutas.Principal.ruta)
                viewmodel.resetFlag()
            }else if (state.navegarRegister){
                navControler.navigateSingleTopTo(Rutas.Register.ruta)
                viewmodel.resetFlag()
            }
            InicioSesionScreen(viewmodel)
        }
        //Navegacion Registro
        composable(Rutas.Register.ruta) {
            val viewmodel: RegisterViewModel=viewModel()
            val state by viewmodel.uiState.collectAsState()
            if (state.navegar){
                navControler.navigateSingleTopTo(Rutas.Login.ruta)
                viewmodel.resetFlag()
            }
            RegisterScreen(viewmodel)
        }
        //Navegacion pantalla Subir Obra
        composable(Rutas.Subir.ruta) {
            val viewmodel: SubirObraViewModel = viewModel()
            val state by viewmodel.uiState.collectAsState()
            if (state.navegar){
                navControler.navigateSingleTopTo(Rutas.Perfil.ruta)
                viewmodel.resetFlag()
            }
            SubirObraScreen(viewmodel)
        }
        //Navegacion Detalles de las obras
        composable("detalle/{obraId}", arguments = listOf(navArgument("obraId"){type = NavType.IntType})) { backStackEntry ->
            val obraId = backStackEntry.arguments?.getInt("obraId") ?: 0
            val obra = ProveedorObras.obras.find { it.obraId == obraId }
            if (obra != null) {
                VistaObrasScreen(obra)
            }
        }
        //Navegacion Pantalla Perfil
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
            val viewmodel: PerfilViewModel = viewModel()
            val state by viewmodel.uiState.collectAsState()
            if (state.navegar){
                navControler.navigateSingleTopTo(Rutas.Detalle.createRoute(state.obra))
                viewmodel.resetFlag()
            }else if (state.guardar){
                navControler.navigateSingleTopTo(Rutas.Guardadas.ruta)
                viewmodel.resetFlag()
            }else if (state.editar){
                navControler.navigateSingleTopTo(Rutas.EditarPerfil.ruta)
                viewmodel.resetFlag()
            }
            PerfilScreen(viewmodel,artista, ProveedorActividad.actividades, ProveedorNotificaciones.notificaciones)
        }
        //Navegacion pantalla buscar
        composable(Rutas.Buscar.ruta) {
            val viewmodel: BuscarViewModel = viewModel()
            BuscarScreen(viewmodel)
        }
        //Navegacion Pantalla Guardados
        composable(Rutas.Guardadas.ruta) {
            val viewmodel: PublicacionesGuardadasViewModel = viewModel()
            val state by viewmodel.uiState.collectAsState()

            if (state.navegar){
                navControler.navigateSingleTopTo(Rutas.Detalle.createRoute(state.obra))
                viewmodel.resetFlag()
            }
            PublicacionesGuardadasScreen(ProveedorObras.obras,viewmodel)
        }
        //Navegacion Pantalla Trending
        composable(Rutas.Trending.ruta) {
            val viewmodel: TrendingViewModel=viewModel ()
            val state by viewmodel.uiState.collectAsState()

            if (state.navegar){
                navControler.navigateSingleTopTo(Rutas.Detalle.createRoute(state.obra))
                viewmodel.resetFlag()
            }
            TrendingScreen(ProveedorObras.obras, viewmodel)
        }
        //Navegacion Pantalla Editar Perfil
        composable(Rutas.EditarPerfil.ruta) {
            val viewmodel: EditarPerfilViewModel = viewModel()
            EditarPerfilScreen(viewmodel)
        }


    }
}
//Barra de navegacion Inferior
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
                onClick = { navController.navigateSingleTopTo(item.route) }
            )
        }
    }
}


//Barra de navegacion Superior
@Composable
fun TopNavigationBar(navControler: NavHostController,modifier: Modifier = Modifier) {
    var busqueda by remember { mutableStateOf("") }
    Column(modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(45.dp))
            // Logo
            LogoTrazzo(
                modifier = Modifier
                    .size(32.dp)
            )


        Button(
            onClick = { navControler.navigateSingleTopTo(Rutas.Buscar.ruta)  },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primaryContainer)
        ) {
            Text("Busca tu inspiracion")

        }

        // Barra de búsqueda




        Spacer(modifier = Modifier.height(8.dp))
    }

}

//Funcion para Invocar pantallas solo una vez a la cola

fun NavController.navigateSingleTopTo(route: String) {
    this.navigate(route) {
        launchSingleTop = true
    }
}


