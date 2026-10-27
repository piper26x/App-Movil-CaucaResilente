package com.example.cauca_resilente_app.ui.Screens

// ui/screens/InformacionAlertasScreen.kt
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cauca_resilente_app.ui.theme.AzulOscuro
import com.example.cauca_resilente_app.ui.theme.FondoClaro
import com.example.cauca_resilente_app.ui.theme.RojoEmergencia
import com.example.cauca_resilente_app.ui.theme.TextoBlanco
import com.example.cauca_resilente_app.ui.theme.TextoOscuro
import com.example.cauca_resilente_app.ui.theme.VerdeSeguro

// Modelo de datos
data class ItemInformacion(
    val categoria: String,
    val colorCategoria: Color,
    val titulo: String,
    val descripcion: String,
    val fecha: String
)

// Datos de ejemplo
private val noticias = listOf(
    ItemInformacion(
        categoria = "Alerta",
        colorCategoria = Color(0xFFF9A825),
        titulo = "Simulacro departamental",
        descripcion = "Este 20 de mayo se realizará el simulacro de respuesta a emergencias en todo el Cauca.",
        fecha = "12/05/2024 • 8:30 a. m."
    )
)

private val alertas = listOf(
    ItemInformacion(
        categoria = "Alerta",
        colorCategoria = RojoEmergencia,
        titulo = "Lluvias intensas en el norte del Cauca",
        descripcion = "Se prevén lluvias fuertes durante las próximas horas. Mantente informado y sigue las recomendaciones.",
        fecha = "12/05/2024 • 7:15 a. m."
    )
)

private val recomendaciones = listOf(
    ItemInformacion(
        categoria = "Recomendación",
        colorCategoria = VerdeSeguro,
        titulo = "Kit de emergencia",
        descripcion = "Ten siempre a mano agua, alimentos no perecederos, linterna y botiquín básico.",
        fecha = "10/05/2024 • 9:00 a. m."
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformacionAlertasScreen(
    onBackClick: () -> Unit,
    onVerMasClick: (ItemInformacion) -> Unit
) {
    var tabSeleccionada by remember { mutableStateOf(0) }
    val tabs = listOf("Noticias", "Alertas", "Recomendaciones")

    Scaffold(
        containerColor = TextoBlanco,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Información y alertas",
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
        ) {
            // ===== TABS =====
            TabRow(
                selectedTabIndex = tabSeleccionada,
                containerColor = TextoBlanco,
                contentColor = AzulOscuro,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        modifier = Modifier
                            .tabIndicatorOffset(tabPositions[tabSeleccionada])
                            .height(3.dp),
                        color = AzulOscuro
                    )
                },
                divider = {
                    HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))
                }
            ) {
                tabs.forEachIndexed { index, titulo ->
                    Tab(
                        selected = tabSeleccionada == index,
                        onClick = { tabSeleccionada = index },
                        text = {
                            Text(
                                titulo,
                                fontSize = 14.sp,
                                fontWeight = if (tabSeleccionada == index) FontWeight.Bold else FontWeight.Normal,
                                color = if (tabSeleccionada == index) AzulOscuro else TextoOscuro.copy(alpha = 0.6f)
                            )
                        }
                    )
                }
            }

            // ===== CONTENIDO SEGÚN TAB =====
            val listaActual = when (tabSeleccionada) {
                0 -> noticias
                1 -> alertas
                else -> recomendaciones
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                contentPadding = PaddingValues(vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(listaActual) { item ->
                    TarjetaInformacion(item = item, onClick = { onVerMasClick(item) })
                }

                // Botón "Ver más información"
                item {
                    Spacer(modifier = Modifier.height(4.dp))
                    Button(
                        onClick = { /* TODO */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = AzulOscuro),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Ver más información", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun TarjetaInformacion(item: ItemInformacion, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = FondoClaro),
        onClick = onClick
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Chip de categoría
            Box(
                modifier = Modifier
                    .background(item.colorCategoria, RoundedCornerShape(50))
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(
                    item.categoria,
                    color = TextoBlanco,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                item.titulo,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = TextoOscuro
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                item.descripcion,
                fontSize = 13.sp,
                color = TextoOscuro.copy(alpha = 0.7f),
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                item.fecha,
                fontSize = 11.sp,
                color = TextoOscuro.copy(alpha = 0.5f)
            )
        }
    }
}