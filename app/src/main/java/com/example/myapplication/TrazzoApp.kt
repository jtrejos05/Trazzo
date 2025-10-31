package com.example.myapplication

import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.currentCompositionContext
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.transition.Transition
import com.example.myapplication.navigation.AppNavigation
import com.example.myapplication.navigation.BottomNavigationBar
import com.example.myapplication.navigation.Rutas
import com.example.myapplication.navigation.TopNavigationBar
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.data.repository.AuthRepository
import com.example.myapplication.navigation.NavegationLogic

@Composable
fun TrazzoApp() {
    val navController = rememberNavController()

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    val context = LocalContext.current
    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted){
                Log.d("Notifica", "ACEPTADO")
            }else{
                Log.d("Notifica", "DENEGADO")
            }
        }
    )

    /*
    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if (ContextCompat.checkSelfPermission(
                context,
                    Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
            ){
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
     */


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