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
import com.example.myapplication.navigation.Rutas
import com.example.myapplication.navigation.TopNavigationBar

import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.ui.unit.dp
import com.example.myapplication.navigation.NavegationLogic

@Composable
fun TrazzoApp() {
    val navController = rememberNavController()

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route


    Scaffold(
            bottomBar = {
                if (NavegationLogic.shouldShowBottomBar(currentRoute)) {
                    BottomNavigationBar(navController)
                }
            },
            topBar = {if (NavegationLogic.shouldShowTopBar(currentRoute)){
                TopNavigationBar(navController)
            } }
        ) {
            AppNavigation(navController, Modifier.padding(it))

        }

}