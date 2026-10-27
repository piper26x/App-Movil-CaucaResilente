package com.example.cauca_resilente_app.ui.Screens

// ui/screens/HomeScreen.kt
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import com.example.cauca_resilente_app.ui.theme.AzulInstitucional
import com.example.cauca_resilente_app.ui.theme.AzulOscuro
import com.example.cauca_resilente_app.ui.theme.FondoClaro
import com.example.cauca_resilente_app.ui.theme.RojoEmergencia
import com.example.cauca_resilente_app.ui.theme.TextoBlanco
import com.example.cauca_resilente_app.ui.theme.TextoOscuro
import com.example.cauca_resilente_app.ui.theme.VerdeSeguro

@Composable
fun HomeScreen(
    userName: String,
    onNotificacionesClick: () -> Unit,
    onMiEstadoClick: () -> Unit,
    onSolicitarAyudaClick: () -> Unit,
    onMiUbicacionClick: () -> Unit,
    onInformacionClick: () -> Unit,
    onVerEnMapaClick: () -> Unit,
    onNavItemSelected: (Int) -> Unit,
    onSosClick: () -> Unit
) {
    Scaffold(
        containerColor = FondoClaro,
        bottomBar = {
            BottomNavBar(
                selectedItem = 0,
                onItemSelected = onNavItemSelected,
                onSosClick = onSosClick
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // ===== ENCABEZADO AZUL =====
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AzulOscuro)
                    .padding(horizontal = 20.dp, vertical = 24.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Hola, $userName 👋",
                            color = TextoBlanco,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Estamos aquí para apoyarte",
                            color = TextoBlanco.copy(alpha = 0.8f),
                            fontSize = 14.sp
                        )
                    }
                    IconButton(
                        onClick = onNotificacionesClick,
                        modifier = Modifier
                            .background(TextoBlanco.copy(alpha = 0.15f), RoundedCornerShape(50))
                    ) {
                        Icon(Icons.Filled.Notifications, contentDescription = "Notificaciones", tint = TextoBlanco)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Tarjeta "Tu estado actual"
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = VerdeSeguro.copy(alpha = 0.15f))
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                "TU ESTADO ACTUAL",
                                fontSize = 11.sp,
                                color = VerdeSeguro,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                "Estoy bien",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = VerdeSeguro
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "Última actualización: 9:41 a. m.",
                                fontSize = 12.sp,
                                color = TextoBlanco.copy(alpha = 0.8f),
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(VerdeSeguro, RoundedCornerShape(50)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Filled.Check, contentDescription = null, tint = TextoBlanco)
                        }
                    }
                }
            }

            // ===== CONTENIDO SCROLLEABLE =====
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                // Grid 2x2 de accesos rápidos
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    QuickAccessCard(
                        icon = Icons.Filled.CheckCircle,
                        iconColor = VerdeSeguro,
                        title = "Mi estado",
                        subtitle = "Actualizar cómo te encuentras",
                        modifier = Modifier.weight(1f),
                        onClick = onMiEstadoClick
                    )
                    QuickAccessCard(
                        icon = Icons.Filled.Warning,
                        iconColor = RojoEmergencia,
                        title = "Solicitar ayuda",
                        subtitle = "Reporta una emergencia",
                        modifier = Modifier.weight(1f),
                        onClick = onSolicitarAyudaClick
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    QuickAccessCard(
                        icon = Icons.Filled.LocationOn,
                        iconColor = AzulOscuro,
                        title = "Mi ubicación",
                        subtitle = "Ver tu última ubicación",
                        modifier = Modifier.weight(1f),
                        onClick = onMiUbicacionClick
                    )
                    QuickAccessCard(
                        icon = Icons.Filled.Info,
                        iconColor = AzulInstitucional,
                        title = "Información",
                        subtitle = "Noticias y alertas importantes",
                        modifier = Modifier.weight(1f),
                        onClick = onInformacionClick
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Tarjeta última ubicación
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = AzulOscuro)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Última ubicación registrada",
                            color = TextoBlanco.copy(alpha = 0.7f),
                            fontSize = 12.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    "Calle 5 # 3-15, Popayán",
                                    color = TextoBlanco,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    "9:41 a. m. • 12/05/2024",
                                    color = TextoBlanco.copy(alpha = 0.7f),
                                    fontSize = 12.sp
                                )
                            }
                            OutlinedButton(
                                onClick = onVerEnMapaClick,
                                border = ButtonDefaults.outlinedButtonBorder.copy(

                                ),
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                            ) {
                                Text("Ver en el mapa", color = TextoBlanco, fontSize = 12.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun QuickAccessCard(
    icon: ImageVector,
    iconColor: Color,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = TextoBlanco),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(28.dp))
            Spacer(modifier = Modifier.height(10.dp))
            Text(title, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
            Spacer(modifier = Modifier.height(2.dp))
            Text(subtitle, fontSize = 11.sp, color = TextoOscuro.copy(alpha = 0.6f), lineHeight = 14.sp)
        }
    }
}