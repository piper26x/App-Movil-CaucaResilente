package com.example.cauca_resilente_app.ui.Screens

// ui/screens/MapaEmergenciaScreen.kt
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cauca_resilente_app.ui.theme.AzulOscuro
import com.example.cauca_resilente_app.ui.theme.FondoClaro
import com.example.cauca_resilente_app.ui.theme.RojoEmergencia
import com.example.cauca_resilente_app.ui.theme.TextoBlanco
import com.example.cauca_resilente_app.ui.theme.TextoOscuro
import com.example.cauca_resilente_app.ui.theme.VerdeSeguro

// Enum de filtros
enum class FiltroMapa(val titulo: String) {
    TODOS("Todos"),
    USUARIOS("Usuarios"),
    AYUDA("Ayuda"),
    ALERTAS("Alertas")
}

// Modelo para los puntos de concentración
data class PuntoConcentracion(
    val x: Float, // posición relativa 0f..1f
    val y: Float,
    val cantidad: Int,
    val color: Color,
    val tipo: FiltroMapa
)

// Datos de ejemplo
private val puntosEjemplo = listOf(
    PuntoConcentracion(0.30f, 0.25f, 3, VerdeSeguro, FiltroMapa.USUARIOS),
    PuntoConcentracion(0.65f, 0.45f, 12, RojoEmergencia, FiltroMapa.ALERTAS),
    PuntoConcentracion(0.20f, 0.55f, 7, Color(0xFFF9A825), FiltroMapa.AYUDA),
    PuntoConcentracion(0.60f, 0.75f, 2, VerdeSeguro, FiltroMapa.USUARIOS)
)

// Posiciones de los marcadores tipo pin
private val marcadoresEjemplo = listOf(
    0.45f to 0.12f,
    0.80f to 0.30f,
    0.25f to 0.78f
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapaEmergenciaScreen(
    onMenuClick: () -> Unit,
    onNavItemSelected: (Int) -> Unit,
    onSosClick: () -> Unit
) {
    var filtroSeleccionado by remember { mutableStateOf(FiltroMapa.TODOS) }

    // Filtrar puntos según selección
    val puntosVisibles = if (filtroSeleccionado == FiltroMapa.TODOS) {
        puntosEjemplo
    } else {
        puntosEjemplo.filter { it.tipo == filtroSeleccionado }
    }

    Scaffold(
        containerColor = FondoClaro,
        bottomBar = {
            BottomNavBar(
                selectedItem = 1,
                onItemSelected = onNavItemSelected,
                onSosClick = onSosClick
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // ===== FONDO TIPO MAPA (Canvas) =====
            MapaCanvasPlaceholder(
                puntos = puntosVisibles,
                marcadores = marcadoresEjemplo
            )

            // ===== ENCABEZADO SUPERPUESTO =====
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                // Barra superior con menú, título y filtro
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(AzulOscuro, RoundedCornerShape(12.dp))
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onMenuClick) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menú", tint = TextoBlanco)
                    }
                    Text(
                        "Mapa de emergencia",
                        color = TextoBlanco,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = { /* TODO: opciones de filtro avanzado */ }) {
                        Icon(Icons.Filled.FilterList, contentDescription = "Filtros", tint = TextoBlanco)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Chips de filtro
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    FiltroMapa.entries.forEach { filtro ->
                        FiltroChip(
                            filtro = filtro,
                            seleccionado = filtroSeleccionado == filtro,
                            onClick = { filtroSeleccionado = filtro }
                        )
                    }
                }
            }

            // ===== BOTÓN DE UBICACIÓN ACTUAL (esquina inferior derecha) =====
            FloatingActionButton(
                onClick = { /* TODO: centrar en ubicación actual */ },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 20.dp, bottom = 20.dp)
                    .size(52.dp),
                containerColor = AzulOscuro,
                contentColor = TextoBlanco,
                shape = CircleShape
            ) {
                Icon(Icons.Filled.MyLocation, contentDescription = "Mi ubicación")
            }
        }
    }
}

@Composable
private fun MapaCanvasPlaceholder(
    puntos: List<PuntoConcentracion>,
    marcadores: List<Pair<Float, Float>>
) {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8F0E3))
    ) {
        val w = size.width
        val h = size.height

        // Dibujar "calles" (líneas blancas)
        drawLine(
            color = Color.White.copy(alpha = 0.7f),
            start = Offset(0f, h * 0.3f),
            end = Offset(w, h * 0.35f),
            strokeWidth = 8f
        )
        drawLine(
            color = Color.White.copy(alpha = 0.7f),
            start = Offset(w * 0.4f, 0f),
            end = Offset(w * 0.45f, h),
            strokeWidth = 8f
        )
        drawLine(
            color = Color.White.copy(alpha = 0.7f),
            start = Offset(0f, h * 0.7f),
            end = Offset(w, h * 0.65f),
            strokeWidth = 6f
        )

        // "Río" (curva azul)
        drawLine(
            color = Color(0xFF9CC8E6),
            start = Offset(w * 0.1f, 0f),
            end = Offset(w * 0.5f, h),
            strokeWidth = 18f
        )

        // Círculos de concentración
        puntos.forEach { punto ->
            val center = Offset(w * punto.x, h * punto.y)
            val radius = 40f + (punto.cantidad * 2f).coerceAtMost(40f)

            // Círculo exterior translúcido
            drawCircle(
                color = punto.color.copy(alpha = 0.25f),
                radius = radius,
                center = center
            )
            // Borde
            drawCircle(
                color = punto.color.copy(alpha = 0.5f),
                radius = radius,
                center = center,
                style = Stroke(width = 4f)
            )
            // Círculo interior sólido con número
            drawCircle(
                color = punto.color,
                radius = radius * 0.4f,
                center = center
            )
        }

        // Marcadores tipo pin (círculos azules con borde blanco)
        marcadores.forEach { (x, y) ->
            val center = Offset(w * x, h * y)
            drawCircle(
                color = Color.White,
                radius = 18f,
                center = center
            )
            drawCircle(
                color = AzulOscuro,
                radius = 13f,
                center = center
            )
        }
    }

    // Capa de texto encima del Canvas (números de los círculos)
    // Se hace aparte porque Canvas no dibuja texto fácilmente
    Box(modifier = Modifier.fillMaxSize()) {
        puntos.forEach { punto ->
            BoxWithConstraints(
                modifier = Modifier.fillMaxSize()
            ) {
                val w = maxWidth
                val h = maxHeight
                // Posicionar el texto en el centro del círculo
                Text(
                    text = punto.cantidad.toString(),
                    color = TextoBlanco,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .offset(
                            x = w * punto.x - 10.dp,
                            y = h * punto.y - 10.dp
                        )
                )
            }
        }
    }
}

@Composable
private fun FiltroChip(
    filtro: FiltroMapa,
    seleccionado: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(
                color = if (seleccionado) AzulOscuro else TextoBlanco,
                shape = RoundedCornerShape(20.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 8.dp)
    ) {
        Text(
            text = filtro.titulo,
            fontSize = 13.sp,
            fontWeight = if (seleccionado) FontWeight.Bold else FontWeight.Medium,
            color = if (seleccionado) TextoBlanco else TextoOscuro.copy(alpha = 0.7f)
        )
    }
}