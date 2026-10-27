package com.example.cauca_resilente_app.ui.Navigation
// ui/navigation/AppNavigation.kt
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cauca_resilente_app.BienvenidaScreen
import com.example.cauca_resilente_app.ui.Screens.HomeScreen
import com.example.cauca_resilente_app.ui.Screens.InformacionAlertasScreen
import com.example.cauca_resilente_app.ui.Screens.LoginScreen
import com.example.cauca_resilente_app.ui.Screens.MapaEmergenciaScreen
import com.example.cauca_resilente_app.ui.Screens.MiEstadoScreen
import com.example.cauca_resilente_app.ui.Screens.MiUbicacionScreen
import com.example.cauca_resilente_app.ui.Screens.PerfilScreen
import com.example.cauca_resilente_app.ui.Screens.SolicitarAyudaScreen

// ui/navigation/AppNavigation.kt
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Bienvenida.route) {

        composable(Screen.Bienvenida.route) {
            BienvenidaScreen(
                onLoginClick = { navController.navigate(Screen.Login.route) },
                onRegisterClick = { navController.navigate(Screen.Login.route) } // temporal
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                onBackClick = { navController.popBackStack() },
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        // Evita volver al login con el botón atrás
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onRegisterClick = { /* TODO pantalla de registro */ },
                onForgotPasswordClick = { /* TODO recuperación */ }
            )
        }

        // composable para la ruta de Home
        composable(Screen.Home.route) {
            HomeScreen(
                userName = "Juan",
                onNotificacionesClick = { /* TODO */ },
                onMiEstadoClick = { navController.navigate(Screen.MiEstado.route) },
                onSolicitarAyudaClick = { navController.navigate(Screen.SolicitarAyuda.route)},
                onMiUbicacionClick = { navController.navigate(Screen.MiUbicacion.route) },
                onInformacionClick = { navController.navigate(Screen.Informacion.route)},
                onVerEnMapaClick = { /* TODO */ },
                onNavItemSelected = { index ->
                    when (index) {
                        1 -> navController.navigate(Screen.Mapa.route)
                        3 -> navController.navigate(Screen.Alertas.route)
                        4 -> navController.navigate(Screen.Perfil.route)
                    }
                },
                onSosClick = { /* TODO */ }
            )
        }

        // composable para la ruta de Mi Estado

        composable (Screen.MiEstado.route){
            MiEstadoScreen(
                onBackClick = { navController.popBackStack() },
                onEstadoSeleccionado = { estado ->
                    // TODO: aquí llamas a tu ViewModel para persistir el estado
                    // viewModel.actualizarEstado(estado)
                }
            )
        }

        // composable para la ruta de solicitar ayuda

        composable (Screen.SolicitarAyuda.route){
            SolicitarAyudaScreen(
                onBackClick = { navController.popBackStack() },
                onEnviarSolicitud = { tipo, descripcion, gravedad ->
                    // TODO: llamar al ViewModel para enviar la solicitud
                    // viewModel.enviarSolicitud(tipo, descripcion, gravedad)
                    navController.popBackStack() // o navegar a una pantalla de confirmación

                })
        }

        // composable para la seccion de informacion
        composable(Screen.Informacion.route) {
            InformacionAlertasScreen(
                onBackClick = { navController.popBackStack() },
                onVerMasClick = { item ->
                    // TODO: navegar al detalle del item
                }
            )
        }

        // composable para el perfil
        composable(Screen.Perfil.route) {
            PerfilScreen(
                onBackClick = { navController.popBackStack() },
                onMisContactosClick = { /* TODO */ },
                onConfiguracionClick = { /* TODO */ },
                onPrivacidadClick = { /* TODO */ },
                onAcercaDeClick = { /* TODO */ },
                onCerrarSesionClick = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true } // limpia toda la pila
                    }
                },
                onNavItemSelected = { index ->
                    when (index) {
                        0 -> navController.navigate(Screen.Home.route)
                        1 -> navController.navigate(Screen.Mapa.route)
                        3 -> navController.navigate(Screen.Alertas.route)
                    }
                },
                onSosClick = { /* TODO */ }
            )
        }


        // composobale para Mi ubicacion
        composable(Screen.MiUbicacion.route) {
            MiUbicacionScreen(
                onBackClick = { navController.popBackStack() },
                onVerEnMapaClick = { navController.navigate(Screen.Mapa.route) },
                onVerHistorialCompletoClick = { /* TODO: pantalla de historial completo */ }
            )
        }

        // composable para el Mapa de emergencia
        composable(Screen.Mapa.route) {
            MapaEmergenciaScreen(
                onMenuClick = { /* TODO: abrir drawer */ },
                onNavItemSelected = { index ->
                    when (index) {
                        0 -> navController.navigate(Screen.Home.route)
                        3 -> navController.navigate(Screen.Alertas.route)
                        4 -> navController.navigate(Screen.Perfil.route)
                    }
                },
                onSosClick = { navController.navigate(Screen.SolicitarAyuda.route) }
            )
        }
    }
}