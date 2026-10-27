package com.example.cauca_resilente_app.ui.Screens
// ui/screens/SolicitarAyudaScreen.kt
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cauca_resilente_app.ui.theme.AzulOscuro
import com.example.cauca_resilente_app.ui.theme.RojoEmergencia
import com.example.cauca_resilente_app.ui.theme.TextoBlanco
import com.example.cauca_resilente_app.ui.theme.TextoOscuro
import com.example.cauca_resilente_app.ui.theme.VerdeSeguro

// Enum para la gravedad
enum class Gravedad(val titulo: String, val color: Color, val colorFondo: Color) {
    BAJA("Baja", VerdeSeguro, VerdeSeguro.copy(alpha = 0.15f)),
    MEDIA("Media", Color(0xFFF9A825), Color(0xFFFFF8E1)),
    ALTA("Alta", RojoEmergencia, RojoEmergencia.copy(alpha = 0.15f))
}

// Opciones del dropdown
private val tiposDeAyuda = listOf(
    "Médica",
    "Rescate",
    "Alimento y agua",
    "Refugio",
    "Apoyo psicológico",
    "Otro"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SolicitarAyudaScreen(
    onBackClick: () -> Unit,
    onEnviarSolicitud: (tipo: String, descripcion: String, gravedad: Gravedad) -> Unit
) {
    var tipoSeleccionado by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var gravedadSeleccionada by remember { mutableStateOf(Gravedad.MEDIA) }
    var dropdownExpandido by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = TextoBlanco,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Solicitar ayuda",
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
                text = "Cuéntanos qué necesitas",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = TextoOscuro
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Tu solicitud será enviada al personal autorizado más cercano.",
                fontSize = 14.sp,
                color = TextoOscuro.copy(alpha = 0.6f),
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(28.dp))

            // ===== DROPDOWN TIPO DE AYUDA =====
            Text(
                "Tipo de ayuda",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextoOscuro
            )
            Spacer(modifier = Modifier.height(8.dp))

            ExposedDropdownMenuBox(
                expanded = dropdownExpandido,
                onExpandedChange = { dropdownExpandido = !dropdownExpandido }
            ) {
                OutlinedTextField(
                    value = tipoSeleccionado,
                    onValueChange = {},
                    readOnly = true,
                    placeholder = { Text("Seleccione", color = TextoOscuro.copy(alpha = 0.4f)) },
                    trailingIcon = {
                        Icon(
                            Icons.Filled.ArrowDropDown,
                            contentDescription = "Abrir opciones",
                            tint = AzulOscuro
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AzulOscuro,
                        unfocusedBorderColor = Color.LightGray
                    )
                )

                ExposedDropdownMenu(
                    expanded = dropdownExpandido,
                    onDismissRequest = { dropdownExpandido = false }
                ) {
                    tiposDeAyuda.forEach { tipo ->
                        DropdownMenuItem(
                            text = { Text(tipo) },
                            onClick = {
                                tipoSeleccionado = tipo
                                dropdownExpandido = false
                            },
                            trailingIcon = {
                                if (tipoSeleccionado == tipo) {
                                    Icon(Icons.Filled.Check, contentDescription = null, tint = VerdeSeguro)
                                }
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ===== DESCRIPCIÓN =====
            Text(
                "Descripción",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextoOscuro
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                placeholder = { Text("Describe tu situación...", color = TextoOscuro.copy(alpha = 0.4f)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                shape = RoundedCornerShape(12.dp),
                maxLines = 6,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AzulOscuro,
                    unfocusedBorderColor = Color.LightGray
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            // ===== GRAVEDAD =====
            Text(
                "Gravedad",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextoOscuro
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Gravedad.entries.forEach { gravedad ->
                    GravedadButton(
                        gravedad = gravedad,
                        seleccionada = gravedadSeleccionada == gravedad,
                        modifier = Modifier.weight(1f),
                        onClick = { gravedadSeleccionada = gravedad }
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // ===== BOTÓN ENVIAR SOS =====
            Button(
                onClick = {
                    onEnviarSolicitud(tipoSeleccionado, descripcion, gravedadSeleccionada)
                },
                enabled = tipoSeleccionado.isNotEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = RojoEmergencia,
                    disabledContainerColor = RojoEmergencia.copy(alpha = 0.4f)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.Send,
                    contentDescription = null,
                    tint = TextoBlanco,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Enviar solicitud SOS",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextoBlanco
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun GravedadButton(
    gravedad: Gravedad,
    seleccionada: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .height(48.dp)
            .background(
                color = if (seleccionada) gravedad.colorFondo else TextoBlanco,
                shape = RoundedCornerShape(10.dp)
            )
            .border(
                width = if (seleccionada) 2.dp else 1.dp,
                color = if (seleccionada) gravedad.color else Color.LightGray,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = gravedad.titulo,
            fontSize = 15.sp,
            fontWeight = if (seleccionada) FontWeight.Bold else FontWeight.Medium,
            color = if (seleccionada) gravedad.color else TextoOscuro.copy(alpha = 0.7f)
        )
    }
}