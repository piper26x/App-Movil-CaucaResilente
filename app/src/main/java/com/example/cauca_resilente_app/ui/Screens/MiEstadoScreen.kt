package com.example.cauca_resilente_app.ui.Screens

// ui/screens/MiEstadoScreen.kt
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cauca_resilente_app.ui.theme.AzulOscuro
import com.example.cauca_resilente_app.ui.theme.GrisInactivo
import com.example.cauca_resilente_app.ui.theme.RojoEmergencia
import com.example.cauca_resilente_app.ui.theme.TextoBlanco
import com.example.cauca_resilente_app.ui.theme.TextoOscuro
import com.example.cauca_resilente_app.ui.theme.VerdeSeguro

// Enum para tipar los estados (evita strings mágicos)
enum class EstadoUsuario(
    val titulo: String,
    val color: Color,
    val colorFondo: Color,
    val icono: ImageVector
) {
    BIEN("Estoy bien", VerdeSeguro, VerdeSeguro.copy(alpha = 0.15f), Icons.Filled.Check),
    AYUDA("Necesito ayuda", Color(0xFFF9A825), Color(0xFFFFF8E1), Icons.Filled.Warning),
    EMERGENCIA("Emergencia", RojoEmergencia, RojoEmergencia.copy(alpha = 0.15f), Icons.Filled.Warning),
    INACTIVO("Sin conexión / Inactivo", GrisInactivo, Color(0xFFEEEEEE), Icons.Filled.Close)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MiEstadoScreen(
    estadoInicial: EstadoUsuario = EstadoUsuario.BIEN,
    onBackClick: () -> Unit,
    onEstadoSeleccionado: (EstadoUsuario) -> Unit
) {
    var estadoActual by remember { mutableStateOf(estadoInicial) }

    Scaffold(
        containerColor = TextoBlanco,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Mi estado",
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
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            Text(
                text = "Informa cómo te encuentras",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = TextoOscuro
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Tu estado será visible para tus contactos autorizados y personal de emergencia.",
                fontSize = 14.sp,
                color = TextoOscuro.copy(alpha = 0.6f),
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Tarjetas de estado
            EstadoUsuario.entries.forEach { estado ->
                EstadoCard(
                    estado = estado,
                    seleccionado = estadoActual == estado,
                    onClick = {
                        estadoActual = estado
                        onEstadoSeleccionado(estado)
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Nota de actualización automática
            Text(
                text = "Tu estado se actualizará automáticamente cada 10 minutos.",
                fontSize = 12.sp,
                color = TextoOscuro.copy(alpha = 0.5f),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun EstadoCard(
    estado: EstadoUsuario,
    seleccionado: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(estado.colorFondo, RoundedCornerShape(16.dp))
            .border(
                width = if (seleccionado) 2.dp else 0.dp,
                color = if (seleccionado) estado.color else Color.Transparent,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Ícono circular con color del estado
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(estado.color, RoundedCornerShape(50)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = estado.icono,
                contentDescription = null,
                tint = TextoBlanco,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = estado.titulo,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = estado.color,
            modifier = Modifier.weight(1f)
        )

        // Indicador de selección
        if (seleccionado) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = "Seleccionado",
                tint = estado.color,
                modifier = Modifier.size(22.dp)
            )
        }
    }
}