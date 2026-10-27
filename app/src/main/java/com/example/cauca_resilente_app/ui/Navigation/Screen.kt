package com.example.cauca_resilente_app.ui.Navigation

// ui/navigation/Screen.kt
sealed class Screen(val route: String) {
    object Bienvenida : Screen("bienvenida")
    object Login : Screen("login")
    object Home : Screen("home")
    object Mapa : Screen("mapa")

    object Alertas : Screen("alertas")

    object Perfil : Screen("perfil")

    object MiEstado : Screen("mi_estado")
    object SolicitarAyuda : Screen("solicitar_ayuda")
    object MiUbicacion : Screen("mi_ubicacion")
    object Informacion : Screen("informacion")


}