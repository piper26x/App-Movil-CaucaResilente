package com.example.cauca_resilente_app.ui.Screens
// ui/screens/MiUbicacionScreen.kt
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cauca_resilente_app.ui.theme.AzulInstitucional
import com.example.cauca_resilente_app.ui.theme.AzulOscuro
import com.example.cauca_resilente_app.ui.theme.FondoClaro
import com.example.cauca_resilente_app.ui.theme.TextoBlanco
import com.example.cauca_resilente_app.ui.theme.TextoOscuro

// Modelo de historial
data class RegistroUbicacion(
    val hora: String,
    val direccion: String
)

private val historialEjemplo = listOf(
    RegistroUbicacion("9:31 a. m.", "Calle 5 # 3-15, Popayán"),
    RegistroUbicacion("9:21 a. m.", "Carrera 6 # 2-10, Popayán"),
    RegistroUbicacion("9:11 a. m.", "Calle 4 # 1-45, Popayán")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MiUbicacionScreen(
    direccionActual: String = "Calle 5 # 3-15, Popayán",
    fechaActual: String = "9:41 a. m. • 12/05/2024",
    historial: List<RegistroUbicacion> = historialEjemplo,
    onBackClick: () -> Unit,
    onVerEnMapaClick: () -> Unit,
    onVerHistorialCompletoClick: () -> Unit
) {
    Scaffold(
        containerColor = TextoBlanco,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Mi ubicación",
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
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {
            // ===== TARJETA DE ÚLTIMA UBICACIÓN =====
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = FondoClaro)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Última ubicación registrada",
                        fontSize = 12.sp,
                        color = TextoOscuro.copy(alpha = 0.6f)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        direccionActual,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextoOscuro
                    )
                    Text(
                        fechaActual,
                        fontSize = 12.sp,
                        color = TextoOscuro.copy(alpha = 0.6f)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // ===== VISTA PREVIA DEL MAPA (placeholder) =====
                    MapaPreview(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Botón "Ver en el mapa"
                    OutlinedButton(
                        onClick = onVerEnMapaClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(10.dp),
                        border = ButtonDefaults.outlinedButtonBorder.copy(),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = AzulOscuro)
                    ) {
                        Icon(Icons.Filled.Map, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Ver en el mapa", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ===== HISTORIAL =====
            Text(
                "Historial de ubicaciones",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = TextoOscuro
            )

            Spacer(modifier = Modifier.height(12.dp))

            historial.forEach { registro ->
                FilaHistorial(registro)
                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Ver historial completo
            TextButton(
                onClick = onVerHistorialCompletoClick,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    "Ver historial completo",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AzulInstitucional
                )
            }
        }
    }
}

@Composable
private fun MapaPreview(modifier: Modifier = Modifier) {
    // Placeholder visual tipo mapa. Reemplazar por GoogleMap cuando tengas la API key.
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(Color(0xFFE8F0E3), Color(0xFFD6E5F2))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // Líneas decorativas tipo calles
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(3.dp)
                .background(Color.White.copy(alpha = 0.6f))
                .align(Alignment.Center)
        )
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(3.dp)
                .background(Color.White.copy(alpha = 0.6f))
                .align(Alignment.Center)
        )

        // Pin central
        Icon(
            Icons.Filled.LocationOn,
            contentDescription = "Ubicación actual",
            tint = AzulOscuro,
            modifier = Modifier.size(40.dp)
        )
    }
}

@Composable
private fun FilaHistorial(registro: RegistroUbicacion) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(FondoClaro, RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            registro.hora,
            fontSize = 13.sp,
            color = TextoOscuro.copy(alpha = 0.6f),
            modifier = Modifier.width(80.dp)
        )
        Text(
            registro.direccion,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = TextoOscuro
        )
    }
}