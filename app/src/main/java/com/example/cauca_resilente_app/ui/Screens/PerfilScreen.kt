package com.example.cauca_resilente_app.ui.Screens

// ui/screens/PerfilScreen.kt
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cauca_resilente_app.ui.theme.AzulOscuro
import com.example.cauca_resilente_app.ui.theme.RojoEmergencia
import com.example.cauca_resilente_app.ui.theme.TextoBlanco
import com.example.cauca_resilente_app.ui.theme.TextoOscuro

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    nombre: String = "Juan Pérez",
    correo: String = "juan.perez@email.com",
    telefono: String = "+57 300 123 4567",
    onBackClick: () -> Unit,
    onMisContactosClick: () -> Unit,
    onConfiguracionClick: () -> Unit,
    onPrivacidadClick: () -> Unit,
    onAcercaDeClick: () -> Unit,
    onCerrarSesionClick: () -> Unit,
    onNavItemSelected: (Int) -> Unit,
    onSosClick: () -> Unit
) {
    Scaffold(
        containerColor = TextoBlanco,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Perfil",
                        color = TextoBlanco,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = TextoBlanco
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = AzulOscuro)
            )
        },
        bottomBar = {
            BottomNavBar(
                selectedItem = 4,
                onItemSelected = onNavItemSelected,
                onSosClick = onSosClick
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            // ===== CABECERA CON AVATAR =====
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avatar circular
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .background(AzulOscuro.copy(alpha = 0.1f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Filled.Person,
                        contentDescription = "Avatar",
                        tint = AzulOscuro,
                        modifier = Modifier.size(40.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        nombre,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextoOscuro
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        correo,
                        fontSize = 13.sp,
                        color = TextoOscuro.copy(alpha = 0.6f)
                    )
                    Text(
                        telefono,
                        fontSize = 13.sp,
                        color = TextoOscuro.copy(alpha = 0.6f)
                    )
                }
            }

            HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))

            Spacer(modifier = Modifier.height(8.dp))

            // ===== OPCIONES =====
            OpcionPerfil(
                icono = Icons.Filled.Contacts,
                titulo = "Mis contactos de emergencia",
                onClick = onMisContactosClick
            )
            OpcionPerfil(
                icono = Icons.Filled.Settings,
                titulo = "Configuración",
                onClick = onConfiguracionClick
            )
            OpcionPerfil(
                icono = Icons.Filled.Lock,
                titulo = "Privacidad y permisos",
                onClick = onPrivacidadClick
            )
            OpcionPerfil(
                icono = Icons.Filled.Info,
                titulo = "Acerca de la app",
                onClick = onAcercaDeClick
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Cerrar sesión (rojo)
            OpcionPerfil(
                icono = Icons.AutoMirrored.Filled.ExitToApp,
                titulo = "Cerrar sesión",
                colorTexto = RojoEmergencia,
                colorIcono = RojoEmergencia,
                onClick = onCerrarSesionClick
            )
        }
    }
}

@Composable
private fun OpcionPerfil(
    icono: ImageVector,
    titulo: String,
    colorTexto: Color = TextoOscuro,
    colorIcono: Color = AzulOscuro,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icono,
            contentDescription = null,
            tint = colorIcono,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            titulo,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = colorTexto,
            modifier = Modifier.weight(1f)
        )
        Icon(
            Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = TextoOscuro.copy(alpha = 0.3f)
        )
    }
}