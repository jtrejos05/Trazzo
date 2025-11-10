package com.example.myapplication.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.myapplication.data.local.ProveedorBotonesDeNavegacion
import com.example.myapplication.ui.Buscar.BuscarScreen
import com.example.myapplication.ui.Buscar.BuscarViewModel
import com.example.myapplication.ui.CrearEditarComment.CrearEditarCommentViewModel
import com.example.myapplication.ui.CrearEditarComment.CrearEditarScreen
import com.example.myapplication.ui.EditarPerfil.EditarPerfilScreen
import com.example.myapplication.ui.EditarPerfil.EditarPerfilViewModel
import com.example.myapplication.ui.Home.HomeScreen
import com.example.myapplication.ui.Home.HomeViewModel
import com.example.myapplication.ui.InicioSesion.InicioSesionScreen
import com.example.myapplication.ui.InicioSesion.InicioSesionViewModel
import com.example.myapplication.ui.Perfil.PerfilScreen
import com.example.myapplication.ui.Perfil.PerfilViewModel
import com.example.myapplication.ui.Principal.PrincipalScreen
import com.example.myapplication.ui.Principal.PrincipalViewModel
import com.example.myapplication.ui.PublicacionesGuardadas.PublicacionesGuardadasScreen
import com.example.myapplication.ui.PublicacionesGuardadas.PublicacionesGuardadasViewModel
import com.example.myapplication.ui.Register.RegisterScreen
import com.example.myapplication.ui.Register.RegisterViewModel
import com.example.myapplication.ui.SeguidoresoSeguidosScreen.SeguidoresoSeguidosScreen
import com.example.myapplication.ui.SeguidoresoSeguidosScreen.SeguidoresoSeguidosViewModel
import com.example.myapplication.ui.Splash.SplashScreen
import com.example.myapplication.ui.Splash.SplashViewModel
import com.example.myapplication.ui.SubirObra.SubirObraScreen
import com.example.myapplication.ui.SubirObra.SubirObraViewModel
import com.example.myapplication.ui.Trending.TrendingScreen
import com.example.myapplication.ui.Trending.TrendingViewModel
import com.example.myapplication.ui.VistasObras.VistaObrasScreen
import com.example.myapplication.ui.VistasObras.VistaObrasViewModel
import com.example.myapplication.ui.utils.LogoTrazzo
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

//Sealed Class con las rutas a las pantallas
sealed class Rutas(
    val ruta: String
    ) {
    object Seguidores: Rutas("seguidores/{perfilId}"){
        fun createSRoute(perfilId: String): String{
            return "seguidores/$perfilId"
        }
    }
    object Seguidos:Rutas("seguidos/{perfilId}"){
        fun createSRoute(perfilId: String): String{
            return "seguidos/$perfilId"
        }
    }

    object Home : Rutas("home")
    object Splash : Rutas("splash")
    object Login : Rutas("login")
    object Register : Rutas("register")
    object Subir : Rutas("subir/{currentUserId}"){
        fun createRoute(currentUserId: String): String {
            return "detalle/$currentUserId"
        }
    }
    object Detalle : Rutas("detalle/{obraId}"){
        fun createRoute(obraId: String): String {
            return "detalle/$obraId"
        }
    }
    object Perfil : Rutas("perfil/{perfilId}"){
        fun createPRoute(perfilId: String): String{
            return "perfil/$perfilId"
        }
    }
    object Buscar : Rutas("buscar")
    object Guardadas : Rutas("guardadas")
    object Trending : Rutas("trending")
    object Barra: Rutas("barra")
    object Principal: Rutas("principal")
    object EditarPerfil: Rutas("editarperfil/{id}"){
        fun createEditRoute(id:String):String{
            return "editarperfil/${id}"
        }
    }
    object CrearComment: Rutas("crearComment/{obraId}"){
        fun createCRoute(obraId: String):String{
            return "crearComment/$obraId"
        }
    }
    object EditarComment: Rutas("editarComment/{commentId}"){
        fun createMRoute(commentId: String): String{
            return "editarComment/$commentId"
        }
    }

}
//Todo lo relacionado a la navegacion entre pantallas
@Composable
fun AppNavigation(navControler: NavHostController,
    modifier: Modifier = Modifier){
    NavHost(navControler, Rutas.Splash.ruta, modifier) {


        //Navegacion Home
        composable(Rutas.Home.ruta) {
            val viewModel: HomeViewModel= hiltViewModel()
            val state by viewModel.uiState.collectAsState()

            HomeScreen(
                { navControler.navigate(Rutas.Login.ruta) },
                { navControler.navigate(Rutas.Register.ruta) }
            )
        }
        //Navegacion Pagina principal
        composable(Rutas.Principal.ruta) {
            val viewmodel: PrincipalViewModel = hiltViewModel()
            val state by viewmodel.uiState.collectAsState()

            PrincipalScreen({obraId->navControler.navigate(Rutas.Detalle.createRoute(obraId ))}, {perfilId->navControler.navigate(Rutas.Perfil.createPRoute(perfilId))},viewmodel)
        }
        //Navegacion Inicio de Sesion
        composable(Rutas.Login.ruta) {
            val viewmodel: InicioSesionViewModel= hiltViewModel()
            val state by viewmodel.uiState.collectAsState()
            if (state.navegar){
                navControler.navigateSingleTopTo(Rutas.Principal.ruta)
                viewmodel.resetFlag()
            }
            InicioSesionScreen(viewmodel, { navControler.navigate(Rutas.Register.ruta) },navControler)
        }
        //Navegacion Registro
        composable(Rutas.Register.ruta) {
            val viewmodel: RegisterViewModel=hiltViewModel()
            val state by viewmodel.uiState.collectAsState()
            if (state.navegar){
                navControler.navigateSingleTopTo(Rutas.Login.ruta)
                viewmodel.resetFlag()
            }
            RegisterScreen(viewmodel)
        }
        //Navegacion pantalla Subir Obra
        composable(Rutas.Subir.ruta, arguments = listOf(navArgument("currentUserId"){type = NavType.StringType})) {
            val currentUserId = it.arguments?.getString("currentUserId") ?: ""
            val viewmodel: SubirObraViewModel = hiltViewModel()
            val state by viewmodel.uiState.collectAsState()
            if (currentUserId != "") {
                SubirObraScreen(viewmodel,
                    { navControler.navigate(Rutas.Perfil.createPRoute(currentUserId))})
            }
        }

        //Navegacion Detalles de las obras
        composable("detalle/{obraId}", arguments = listOf(navArgument("obraId"){type = NavType.StringType})) {
            val obraId = it.arguments?.getString("obraId") ?: ""
            val viewmodel: VistaObrasViewModel = hiltViewModel()
            val state by viewmodel.uiState.collectAsState()
            if (obraId != "") {
                VistaObrasScreen(obraId,{obraId->navControler.navigate(Rutas.CrearComment.createCRoute(obraId))},{},viewmodel)
            }
        }
        composable(Rutas.Seguidores.ruta, arguments = listOf(navArgument("perfilId"){type = NavType.StringType})){
            val UsuarioId=it.arguments?.getString("perfilId") ?:""
            val viewmodel: SeguidoresoSeguidosViewModel = hiltViewModel()
            SeguidoresoSeguidosScreen(viewmodel, UsuarioId, false)
        }
        composable(Rutas.Seguidos.ruta, arguments = listOf(navArgument("perfilId"){type = NavType.StringType})){
            val UsuarioId=it.arguments?.getString("perfilId") ?:""
            val viewmodel: SeguidoresoSeguidosViewModel = hiltViewModel()
            SeguidoresoSeguidosScreen(viewmodel,UsuarioId,true)
        }
        composable(Rutas.CrearComment.ruta, arguments = listOf(navArgument("obraId"){type = NavType.StringType})){
            val obraId = it.arguments?.getString("obraId") ?: ""
            val viewModel: CrearEditarCommentViewModel = hiltViewModel()
            val stat by viewModel.uiState.collectAsState()
            if (obraId != ""){
                CrearEditarScreen(obraId=obraId,viewModel=viewModel, onBack = {navControler.navigate(Rutas.Principal.ruta)})
            }

        }

        composable(Rutas.EditarComment.ruta,arguments = listOf(navArgument("commentId"){type = NavType.StringType})) {
            val commentId = it.arguments?.getString("commentId") ?: ""

            val viewModel: CrearEditarCommentViewModel = hiltViewModel()
            val stat by viewModel.uiState.collectAsState()
            if (commentId != "") {
                CrearEditarScreen(
                    id = commentId,
                    obraId = "0",
                    viewModel = viewModel,
                    onBack = { navControler.navigate(Rutas.Principal.ruta) })
            }
        }


        //Navegacion Pantalla Perfil
        composable(Rutas.Perfil.ruta, arguments = listOf(navArgument("perfilId"){type = NavType.StringType})) {
            val UsuarioId=it.arguments?.getString("perfilId") ?:""
            val viewmodel: PerfilViewModel = hiltViewModel()
            val state by viewmodel.uiState.collectAsState()
            PerfilScreen(UsuarioId, viewmodel,
                {navControler.navigate(
                    Rutas.Guardadas.ruta)}
                ,{ obraId->navControler.navigate(Rutas.Detalle.createRoute(obraId )) },
                {navControler.navigate(Rutas.EditarPerfil.createEditRoute(UsuarioId))}, {navControler.navigate(Rutas.Home.ruta)},
                {commentId->

                    navControler.navigate(Rutas.EditarComment.createMRoute(commentId))
                }, {userId->navControler.navigate(Rutas.Seguidores.createSRoute(userId))}, {userId->navControler.navigate(
                    Rutas.Seguidos.createSRoute(userId))})
        }

        // Navegacion pantalla buscar
        composable(Rutas.Buscar.ruta) {
            val viewmodel: BuscarViewModel = hiltViewModel()

            BuscarScreen(
                viewmodel = viewmodel,
                onCategoriaClick = { categoria -> viewmodel.seleccionarCategoria(categoria) },
                onTrendingClick = { trending -> viewmodel.seleccionarTrending(trending) },
                {obraId->navControler.navigate(Rutas.Detalle.createRoute(obraId ))}
            )
        }
        //Navegacion Pantalla Guardados
        composable(Rutas.Guardadas.ruta) {
            val viewmodel: PublicacionesGuardadasViewModel = hiltViewModel()
            val state by viewmodel.uiState.collectAsState()

            PublicacionesGuardadasScreen({ obraId->navControler.navigate(Rutas.Detalle.createRoute(obraId )) },viewmodel)
        }
        //Navegacion Pantalla Trending
        composable(Rutas.Trending.ruta) {
            val viewmodel: TrendingViewModel=hiltViewModel()
            val state by viewmodel.uiState.collectAsState()
            TrendingScreen( viewmodel,{ obraId->navControler.navigate(Rutas.Detalle.createRoute(obraId )) }, {perfilId->navControler.navigate(Rutas.Perfil.createPRoute(perfilId))})
        }
        //Navegacion Pantalla Editar Perfil
        composable(Rutas.EditarPerfil.ruta, arguments = listOf(navArgument("id"){type = NavType.StringType})) {
            val UsuarioId=it.arguments?.getString("id") ?:""
            val viewmodel: EditarPerfilViewModel = hiltViewModel()
            val state by viewmodel.uiState.collectAsState()
            if (state.navegar){
                navControler.navigateSingleTopTo(Rutas.Perfil.createPRoute(UsuarioId))
                viewmodel.resetFlag()
            }
            EditarPerfilScreen(UsuarioId,viewmodel)
        }
        //Navegacion Splash Screen
        composable(Rutas.Splash.ruta){
            val viewModel: SplashViewModel = hiltViewModel()
            SplashScreen(
                navigateToPrincipal = {
                    navControler.navigate(Rutas.Principal.ruta){
                        popUpTo(0){inclusive = true}
                    }
                },
                navigateToInicio = {
                    navControler.navigate(Rutas.Home.ruta){
                        popUpTo(0){inclusive=true}
                    }
                },
                viewModel
            )
        }


    }
}
//Barra de navegacion Inferior
@Composable
fun BottomNavigationBar (
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
                        contentDescription = item.route
                    )
                } ,
                selected = false,
                onClick = {
                    if (item.route == Rutas.Perfil.ruta){
                        navController.navigateSingleTopTo(Rutas.Perfil.createPRoute(FirebaseAuth.getInstance().currentUser?.uid ?: ""))
                    }else if(item.route == Rutas.Subir.ruta){
                        navController.navigateSingleTopTo(Rutas.Subir.createRoute(FirebaseAuth.getInstance().currentUser?.uid ?: ""))
                    } else{
                        navController.navigateSingleTopTo(item.route)
                    }
                },
                modifier = Modifier.testTag(item.route)
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


