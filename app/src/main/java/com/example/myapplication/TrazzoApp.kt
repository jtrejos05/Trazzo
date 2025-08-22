package com.example.myapplication

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.transition.Transition
import com.example.myapplication.navigation.AppNavigation
import com.example.myapplication.navigation.BottomNavigationBar
import com.example.myapplication.navigation.NavigationBar
import com.example.myapplication.navigation.Rutas
import com.example.myapplication.navigation.TopNavigationBar
import com.example.myapplication.ui.HomeScreen
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun TrazzoApp() {
    val navController = rememberNavController()

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    val ShowBottom = currentRoute == Rutas.Perfil.ruta
    val ShowAll = currentRoute == Rutas.Guardadas.ruta || currentRoute == Rutas.Trending.ruta
        Scaffold(
            bottomBar = {
                if (ShowBottom) {
                    BottomNavigationBar(navController)
                }
            },
            topBar = {
                 if (ShowAll){
                    TopNavigationBar()
                }
            }
        ) {
            AppNavigation(navController, Modifier.padding(it))

        }

}