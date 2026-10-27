package com.example.cauca_resilente_app.ui.Screens

// ui/components/BottomNavBar.kt
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import com.example.cauca_resilente_app.ui.theme.AmarilloInstitucional
import com.example.cauca_resilente_app.ui.theme.AzulOscuro
import com.example.cauca_resilente_app.ui.theme.RojoEmergencia
import com.example.cauca_resilente_app.ui.theme.TextoBlanco

@Composable
fun BottomNavBar(
    selectedItem: Int,
    onItemSelected: (Int) -> Unit,
    onSosClick: () -> Unit
) {
    val items = listOf(
        Triple("Inicio", Icons.Filled.Home, 0),
        Triple("Mapa", Icons.Filled.Map, 1),
        Triple("Alertas", Icons.Filled.Notifications, 3),
        Triple("Perfil", Icons.Filled.Person, 4)
    )

    Box(modifier = Modifier.fillMaxWidth()) {
        NavigationBar(containerColor = TextoBlanco) {
            // Ítems 0 y 1
            items.take(2).forEach { (label, icon, index) ->
                NavigationBarItem(
                    selected = selectedItem == index,
                    onClick = { onItemSelected(index) },
                    icon = { Icon(icon, contentDescription = label) },
                    label = { Text(label, fontSize = 11.sp,) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = AzulOscuro,
                        selectedTextColor = AzulOscuro,
                        indicatorColor = AmarilloInstitucional.copy(alpha = 0.3f)
                    )
                )
            }

            // Hueco central para el botón SOS
            Spacer(modifier = Modifier.weight(1f))

            // Ítems 3 y 4
            items.drop(2).forEach { (label, icon, index) ->
                NavigationBarItem(
                    selected = selectedItem == index,
                    onClick = { onItemSelected(index) },
                    icon = { Icon(icon, contentDescription = label) },
                    label = { Text(label, fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = AzulOscuro,
                        selectedTextColor = AzulOscuro,
                        indicatorColor = AmarilloInstitucional.copy(alpha = 0.3f)
                    )
                )
            }
        }

        // Botón SOS flotante en el centro
        FloatingActionButton(
            onClick = onSosClick,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-20).dp)
                .size(64.dp),
            shape = CircleShape,
            containerColor = RojoEmergencia,
            contentColor = TextoBlanco
        ) {
            Text("SOS", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
    }
}