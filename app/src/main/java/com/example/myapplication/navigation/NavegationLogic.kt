package com.example.myapplication.navigation

object NavegationLogic {
    //pantallas que no tienen la Barra inferior
    private val noBottomBarScreens= listOf(
        Rutas.Login.ruta,
        Rutas.Register.ruta,
        Rutas.Home.ruta
    )
    //pantallas que no tienen la barra superior
    private val noTopBarScreens= listOf(
        Rutas.Subir.ruta,
        Rutas.Login.ruta,
        Rutas.Register.ruta,
        Rutas.Home.ruta,
        Rutas.Buscar.ruta,
        Rutas.Perfil.ruta,
        Rutas.EditarPerfil.ruta
    )
    //funciones para saber si poner o no las barras
    fun shouldShowBottomBar(currentRoute: String?): Boolean {
        return currentRoute !in noBottomBarScreens
    }
    fun shouldShowTopBar(currentRoute: String?): Boolean {
        return currentRoute !in noTopBarScreens
    }
}